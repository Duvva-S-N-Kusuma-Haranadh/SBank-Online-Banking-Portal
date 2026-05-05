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
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="transactions")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Transaction {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="from_account_id", nullable=true)
	private Account fromAccount;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="to_account_id", nullable=true)
	private Account toAccount;
		
	@Column(nullable=false, precision=15, scale=2)
	private BigDecimal amount;
	
	@Enumerated(EnumType.STRING)
	@Column(name="transaction_type")
	private TransactionType transactionType;
		
	@Column(length=255)
	private String description;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private TransactionStatus status = TransactionStatus.PENDING;
	
	@Column(name="transaction_date")
	private LocalDateTime transactionDate = LocalDateTime.now();
	
	public enum TransactionType {
		CREDIT, DEBIT, TRANSFER, BILL_PAYMENT
	}
	
	public enum TransactionStatus {
		PENDING, SUCCESS, FAILED
	}
}
