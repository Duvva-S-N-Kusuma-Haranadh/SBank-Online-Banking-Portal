package com.haranadh.SBank.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LockerResponse {
	private Long lockerId;
	private String lockerNumber;
	private String lockerSize;
	private String branchName;
	private String status;
	private BigDecimal annualRent;
	private LocalDateTime assignedOn;
	
	private String assignedToUser;
}
