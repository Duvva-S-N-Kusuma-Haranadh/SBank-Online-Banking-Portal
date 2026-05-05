package com.haranadh.SBank.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransferRequest {
	private String fromAccountNumber;
	
	private String toAccountNumber;
	
	private BigDecimal amount;
	
	private String description;
}