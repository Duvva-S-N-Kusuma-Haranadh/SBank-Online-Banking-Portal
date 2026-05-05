package com.haranadh.SBank.Entities;

import java.math.BigDecimal;
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

@Entity(name="loans")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Loan {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="user_id", nullable=false)
	private User user;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "loan_type", nullable = false)
	private LoanType loanType;
	
	@Column(nullable = false, precision=15,scale=2)
	private BigDecimal principal;
	
	@Column(name="interest_rate", nullable = false, precision = 5, scale=2)
	private BigDecimal interestRate;
	
	@Column(name="tenure_months", nullable=false)
	private Integer tenureMonths;
	
	@Column(name="emi_amount", nullable=false, precision = 15, scale= 2)
	private BigDecimal emiAmount;
	
	@Column(name="paid_months")
	private Integer paidMonths;
	
	@Enumerated(EnumType.STRING)
	@Column(name="loan_status")
	private LoanStatus loanStatus = LoanStatus.APPLIED;
	
	@Column(name="applied_on")
	private LocalDateTime appliedOn = LocalDateTime.now();
	
	@Column(name="approved_on")
	private LocalDateTime approvedOn;
	
	public enum LoanType {
        HOME, PERSONAL, EDUCATION, CAR, BUSINESS
    }
	
	public enum LoanStatus {
		APPLIED, APPROVED, ACTIVE, CLOSED, REJECTED
	}
}
