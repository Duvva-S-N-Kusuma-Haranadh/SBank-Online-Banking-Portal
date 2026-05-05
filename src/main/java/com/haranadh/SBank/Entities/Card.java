package com.haranadh.SBank.Entities;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "cards")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Card {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="user_id", nullable=false)
	private User user;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="account_id", nullable=false)
	private Account account;

	@Column(name = "card_number", unique = true, nullable = false, length = 20)
	private String cardNumber;

	@Enumerated(EnumType.STRING)
	@Column(name="card_type", nullable=false)
	private CardType cardType;

	@Enumerated(EnumType.STRING)
	@Column(name="card_status", nullable=false)
	private CardStatus cardStatus = CardStatus.PENDING;

	@Column(name="expiry_date", nullable=true)
	private LocalDate expiryDate;

	@Column(name="credit_limit", precision = 15, scale = 2)
	private BigDecimal creditLimit = BigDecimal.ZERO;
	
	@Column(name="applied_on")
	private LocalDateTime appliedOn = LocalDateTime.now();
	
	public enum CardType {
		ATM, DEBIT, CREDIT, GIFT
	}
	
	public enum CardStatus {
		ACTIVE, BLOCKED, EXPIRED, PENDING
	}
}
