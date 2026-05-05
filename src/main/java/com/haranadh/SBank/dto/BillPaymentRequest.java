package com.haranadh.SBank.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BillPaymentRequest {
	private String fromAccountNumber;
	private String billType;
	private String billNumber;
	private BigDecimal amount;
	private String description;
}
