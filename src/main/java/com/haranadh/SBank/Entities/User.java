package com.haranadh.SBank.Entities;

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
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="users")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column(unique=true, nullable=false, length=50)
	private String username;
	
	@Column(nullable=false)
	private String password;
	
	@Column(unique=true, nullable=false, length=100)
	private String email;
	
	@Column(nullable=false, length=15)
	private String phone;
	
	@Column(name="full_name", nullable=false, length=100)
	private String fullName;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable=false)
	private Role role = Role.CUSTOMER;
	
	@Column(name="is_active")
	private Boolean isActive = true;
	
	@Column(name="created_at")
	private LocalDateTime createdAt = LocalDateTime.now();
	
	@OneToMany(mappedBy = "user", cascade=CascadeType.ALL, fetch=FetchType.LAZY)
	@JsonIgnore
	private List<Account> accounts;
	
	@OneToMany(mappedBy = "user", cascade=CascadeType.ALL, fetch=FetchType.LAZY)
	@JsonIgnore
	private List<Card> cards;
	
	@OneToMany(mappedBy ="user", cascade=CascadeType.ALL,fetch=FetchType.LAZY)
	@JsonIgnore
	private List<Loan> loans;
	
	@OneToOne(mappedBy = "user", cascade=CascadeType.ALL, fetch=FetchType.LAZY)
	private Locker locker;
	
	public enum Role {
		CUSTOMER, ADMIN
	}
}
