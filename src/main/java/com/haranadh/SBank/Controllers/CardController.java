package com.haranadh.SBank.Controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.haranadh.SBank.Entities.Card;
import com.haranadh.SBank.Services.CardService;
import com.haranadh.SBank.dto.ApiResponse;
import com.haranadh.SBank.dto.CardResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/cards")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class CardController {
	private final CardService cardService;

	@GetMapping("/user/{userId}")
	public ResponseEntity<ApiResponse<List<CardResponse>>> getCardsByUserId(@PathVariable Long userId) {
		try {
			List<CardResponse> cards = cardService.getCardsByUserId(userId);
			return ResponseEntity.ok(ApiResponse.success("Cards fetched successfully", cards));
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
		}
	}

	@GetMapping("/user/{userId}/type")
	public ResponseEntity<ApiResponse<List<CardResponse>>> getCardsByType(@PathVariable Long userId,
			@RequestParam Card.CardType cardType) {
		try {
			List<CardResponse> cards = cardService.getCardsByType(userId, cardType);
			return ResponseEntity.ok(ApiResponse.success("Cards fetched by type", cards));
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
		}
	}

	@PostMapping("/apply")
	public ResponseEntity<ApiResponse<CardResponse>> applyCard(@RequestParam Long userId, @RequestParam Long accountId,
			@RequestParam Card.CardType cardType, @RequestParam String cardNumber) {
		try {
			CardResponse card = cardService.applyCard(userId, accountId, cardType, cardNumber);
			return ResponseEntity.ok(ApiResponse.success("Card application submitted", card));
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
		}
	}

	@PutMapping("/{cardNumber}/block")
	public ResponseEntity<ApiResponse<String>> blockCard(@PathVariable String cardNumber) {
		try {
			String message = cardService.blockCard(cardNumber);
			return ResponseEntity.ok(ApiResponse.success(message));
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
		}
	}

	@PutMapping("/{cardNumber}/activate")
	public ResponseEntity<ApiResponse<String>> activateCard(@PathVariable String cardNumber) {
		try {
			String message = cardService.activateCard(cardNumber);
			return ResponseEntity.ok(ApiResponse.success(message));
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
		}
	}

	@GetMapping("user/{userId}/expiring-soon")
	public ResponseEntity<ApiResponse<List<CardResponse>>> getExpiringSonn(@PathVariable Long userId) {
		try {
			List<CardResponse> cards = cardService.getCardsExpiringSoon(userId);
			return ResponseEntity.ok(ApiResponse.success("Expiring cards fetched", cards));
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
		}
	}
}
