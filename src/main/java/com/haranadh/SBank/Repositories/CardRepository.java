package com.haranadh.SBank.Repositories;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.haranadh.SBank.Entities.Card;

@Repository
public interface CardRepository extends JpaRepository<Card, Long> {
	Optional<Card> findByCardNumber(String cardNumber);
	
	boolean existsByCardNumber(String cardNumber);
	
	List<Card> findByUserId(Long userId);
	
	List<Card> findByUserIdAndCardType(Long userId, Card.CardType cardType);
	
	List<Card> findByUserIdAndCardStatus(Long userId, Card.CardStatus cardStatus);
	
	List<Card> findByUserIdAndCardStatusNot(Long userId, Card.CardStatus cardStatus);
	
	List<Card> findByAccountId(Long accountId);
	
	List<Card> findByExpiryDateBefore(LocalDate date);
	
	List<Card> findByExpiryDateBetween(LocalDate today, LocalDate next30Days);

	long countByUserId(Long userId);
	
	long countByUserIdAndCardStatus(Long userId, Card.CardStatus cardStatus);
	
	List<Card> findByCardStatus(Card.CardStatus cardStatus);
}
