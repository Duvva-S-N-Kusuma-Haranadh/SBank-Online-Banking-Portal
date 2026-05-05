package com.haranadh.SBank.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionResponse {
	private Long transactionId;
	private BigDecimal amount;
	private String transactionType;
	private String description;
	private String status;
	private LocalDateTime transactionDate;
	
	private String fromAccountNumber;
	private String toAccountNumber;
}
