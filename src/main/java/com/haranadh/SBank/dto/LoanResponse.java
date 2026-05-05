package com.haranadh.SBank.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.haranadh.SBank.Entities.Loan.LoanStatus;
import com.haranadh.SBank.Entities.Loan.LoanType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoanResponse {
	private Long loanId;
	private LoanType loanType;
	private BigDecimal principal;
	private BigDecimal interestRate;
	private Integer tenureMonths;
	private BigDecimal emiAmount;
	private Integer paidMonths;
	private Integer remianingMonths;
	private BigDecimal totalPaidAmount;
	private BigDecimal remainingAmount;
	private LoanStatus loanStatus;
	private LocalDateTime appliedOn;
	private LocalDateTime approvedOn;
}
