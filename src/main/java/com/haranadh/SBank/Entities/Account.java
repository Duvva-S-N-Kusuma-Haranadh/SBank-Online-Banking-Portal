package com.haranadh.SBank.Entities;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
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
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "accounts")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Account {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = false)
	private User user;

	@Column(name = "account_number", unique = true, nullable = false, length = 20)
	private String accountNumber;

	@Enumerated(EnumType.STRING)
	@Column(name = "account_type")
	private AccountType accountType = AccountType.SAVINGS;

	@Column(nullable = false, precision = 15, scale = 2)
	private BigDecimal balance = BigDecimal.ZERO;

	@Column(name = "ifsc_code", nullable = false, length = 20)
	private String ifscCode;

	@Column(name = "branch_name", nullable = false, length = 100)
	private String branchName;

	@Column(name = "is_active")
	private Boolean isActive = true;

	@Column(name = "created_at")
	private LocalDateTime createdAt = LocalDateTime.now();

	@OneToMany(mappedBy = "fromAccount", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JsonIgnore
	private List<Transaction> sentTransactions;

	@OneToMany(mappedBy = "toAccount", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JsonIgnore
	private List<Transaction> receivedTransactions;

	@OneToMany(mappedBy = "account", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JsonIgnore
	private List<Card> cards;

	public enum AccountType {
		SAVINGS, CURRENT, SALARY
	}
}
