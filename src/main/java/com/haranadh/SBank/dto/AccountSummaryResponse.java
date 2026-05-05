package com.haranadh.SBank.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountSummaryResponse {
	private Long accountId;
	
	private String accountNumber;
	
	private String accountType;
	
	private BigDecimal balance;
	
	private String ifscCode;
	
	private String branchName;
	
	private Boolean isActive;
	
	private LocalDateTime createdAt;
	
	private String ownerName;
	
	private String ownerEmail;
	
	private String ownerPhone;
}
