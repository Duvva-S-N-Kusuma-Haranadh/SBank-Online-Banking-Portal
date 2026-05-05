package com.haranadh.SBank.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class CardResponse {
	private Long cardId;
	private String cardNumber;
	private String cardType;
	private String cardStatus;
	private LocalDate expiryDate;
	private BigDecimal creditLimit;
	private LocalDateTime appliedOn;
	
	private String linkedAccountNumber;
}
