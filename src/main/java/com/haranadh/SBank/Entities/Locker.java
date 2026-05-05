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
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name="lockers")
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Locker {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="user_id", nullable=true)
	private User user;
	
	@Column(name="locker_number", unique=true, nullable=false, length=20)
	private String lockerNumber;
	
	@Enumerated(EnumType.STRING)
	@Column(name="locker_size", unique=false)
	private LockerSize lockerSize;
	
	@Column(name="branch_name", nullable=false, length = 100)
	private String branchName;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable=false)
	private LockerStatus status = LockerStatus.AVAILABLE;
	
	@Column(name="assigned_on")
	private LocalDateTime assignedOn;
	
	@Column(name="annual_rent", nullable=false, precision = 10, scale=2)
	private BigDecimal annualRent;
	
	public enum LockerSize {
		SMALL, MEDIUM, LARGE
	}
	
	public enum LockerStatus {
		AVAILABLE, OCCUPIED
	}
}
