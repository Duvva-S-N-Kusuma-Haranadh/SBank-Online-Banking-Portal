package com.haranadh.SBank.Services;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.haranadh.SBank.Entities.Account;
import com.haranadh.SBank.Entities.Card;
import com.haranadh.SBank.Entities.User;
import com.haranadh.SBank.Repositories.AccountRepository;
import com.haranadh.SBank.Repositories.CardRepository;
import com.haranadh.SBank.Repositories.UserRepository;
import com.haranadh.SBank.dto.CardResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CardService {
	private final CardRepository cardRepository;
	private final UserRepository userRepository;
	private final AccountRepository accountRepository;
	
	public List<CardResponse> getCardsByUserId(Long userId) {
		return cardRepository.findByUserId(userId)
				.stream()
				.map(this::mapToResponse)
				.collect(Collectors.toList());
	}
	
	public List<CardResponse> getCardsByType(Long userId, Card.CardType cardType) {
		return cardRepository.findByUserIdAndCardType(userId, cardType)
				.stream()
				.map(this::mapToResponse)
				.collect(Collectors.toList());
	}
	
	public CardResponse applyCard(Long userId, Long accountId, Card.CardType cardType, String cardNumber) {
		User user = userRepository.findById(userId)
				.orElseThrow(()->new RuntimeException("user not found"));
		Account account = accountRepository.findById(accountId)
				.orElseThrow(() -> new RuntimeException("Account not Found"));
		
		if(cardRepository.existsByCardNumber(cardNumber)) {
			throw new RuntimeException("Card number already exists");
		}
		
		Card card = new Card();
		card.setUser(user);
		card.setAccount(account);
		card.setCardNumber(cardNumber);
		card.setCardType(cardType);
		card.setCardStatus(Card.CardStatus.PENDING);
		card.setExpiryDate(LocalDate.now().plusYears(5));
		card.setAppliedOn(java.time.LocalDateTime.now());
		
		Card saved = cardRepository.save(card);
		return mapToResponse(saved);
	}
	
	public String blockCard(String cardNumber) {
		Card card = cardRepository.findByCardNumber(cardNumber).orElseThrow(() -> new RuntimeException("Card not found."));
		
		card.setCardStatus(Card.CardStatus.BLOCKED);
		cardRepository.save(card);
		
		return "Card blocked successfully";
	}
	
	public String activateCard(String cardNumber) {
		Card card = cardRepository.findByCardNumber(cardNumber).orElseThrow(()->new RuntimeException("card not found"));
		
		if(card.getCardStatus()==Card.CardStatus.EXPIRED) {
			throw new RuntimeException("Card is expired and cananot be activated");
		}
		
		card.setCardStatus(Card.CardStatus.ACTIVE);
		cardRepository.save(card);
		
		return "Card activated successfully";
	}
	
	public List<CardResponse> getCardsExpiringSoon(Long userId) {
		LocalDate today = LocalDate.now();
		LocalDate next30Days = today.plusDays(30);
		
		return cardRepository.findByExpiryDateBetween(today, next30Days)
				.stream()
				.filter(c -> c.getUser().getId().equals(userId))
				.map(this::mapToResponse)
				.collect(Collectors.toList());
	}
	
	private String maskCardNumber(String cardNumber) {
		if(cardNumber == null || cardNumber.length() < 4) return "****";
		
		return "**** **** **** " + cardNumber.substring(cardNumber.length() - 4);
	}
	
	private CardResponse mapToResponse(Card card) {

        CardResponse response = new CardResponse();
        response.setCardId(card.getId());
        response.setCardNumber(maskCardNumber(card.getCardNumber()));
        response.setCardType(card.getCardType().name());
        response.setCardStatus(card.getCardStatus().name());
        response.setExpiryDate(card.getExpiryDate());
        response.setCreditLimit(card.getCreditLimit());
        response.setAppliedOn(card.getAppliedOn());
        response.setLinkedAccountNumber(card.getAccount().getAccountNumber());
        return response;
    }
}