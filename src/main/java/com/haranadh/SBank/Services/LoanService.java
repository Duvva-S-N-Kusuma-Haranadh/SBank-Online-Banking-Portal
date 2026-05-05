package com.haranadh.SBank.Services;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.haranadh.SBank.Entities.Loan;
import com.haranadh.SBank.Entities.User;
import com.haranadh.SBank.Repositories.LoanRepository;
import com.haranadh.SBank.Repositories.UserRepository;
import com.haranadh.SBank.dto.LoanResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LoanService {
	private final LoanRepository loanRepository;
	private final UserRepository userRepository;

	public List<LoanResponse> getLoansByUserId(Long userId) {
		return loanRepository.findByUserId(userId).stream().map(this::mapToResponse).collect(Collectors.toList());
	}

	public List<LoanResponse> getLoansByStatus(Long userId, Loan.LoanStatus status) {
		return loanRepository.findByUserIdAndLoanStatus(userId, status).stream().map(this::mapToResponse)
				.collect(Collectors.toList());
	}

	public LoanResponse getLoanById(Long loanId, Long userId) {
		Loan loan = loanRepository.findByIdAndUserId(loanId, userId)
				.orElseThrow(() -> new RuntimeException("Loan not found"));

		return mapToResponse(loan);
	}
	
	public LoanResponse applyLoan(Long userId, Loan loan) {
		User user = userRepository.findById(userId)
				.orElseThrow(()->new RuntimeException("User not found"));
		
		boolean alreadyExists = loanRepository.existsByUserIdAndLoanTypeAndLoanStatus(userId, loan.getLoanType(),
				Loan.LoanStatus.ACTIVE);
		
		if(alreadyExists) {
			throw new RuntimeException("You already have an active " + loan.getLoanType().name() + " loan");
		}
		
		BigDecimal emi = calculateEmi(loan.getPrincipal(), loan.getInterestRate(), loan.getTenureMonths());
		
		loan.setUser(user);
		loan.setEmiAmount(emi);
		loan.setLoanStatus(Loan.LoanStatus.APPLIED);
		loan.setPaidMonths(0);
		loan.setAppliedOn(LocalDateTime.now());
		
		Loan saved = loanRepository.save(loan);
		return mapToResponse(saved);
	}
	
	public LoanResponse approvedLoan(Long loanId) {
		Loan loan = loanRepository.findById(loanId).orElseThrow(()-> new RuntimeException("Loan not found"));
		
		if(loan.getLoanStatus() != Loan.LoanStatus.APPLIED) {
			throw new RuntimeException("Only APPLIED loans can be approved");
		}
		
		loan.setLoanStatus(Loan.LoanStatus.APPROVED);
		loan.setApprovedOn(LocalDateTime.now());
		
		Loan saved = loanRepository.save(loan);
		return mapToResponse(saved);
	}
	
	public LoanResponse payEmi(Long loanId, Long userId) {
		Loan loan = loanRepository.findByIdAndUserId(loanId, userId)
				.orElseThrow(()-> new RuntimeException("Loan not found"));
		
		if(loan.getLoanStatus() != Loan.LoanStatus.ACTIVE) {
			throw new RuntimeException("Loan is not ACTIVE");
		}
		
		loan.setPaidMonths(loan.getPaidMonths() + 1);
		
		if(loan.getPaidMonths().equals(loan.getTenureMonths())) {
			loan.setLoanStatus(Loan.LoanStatus.CLOSED);
		}
		
		Loan saved = loanRepository.save(loan);
		return mapToResponse(saved);
	}
	
	public BigDecimal calculateEmi(BigDecimal principal, BigDecimal annualRate, int tenureMonths) {
		double p = principal.doubleValue();
		double r = annualRate.doubleValue();
		double n = tenureMonths;
		
		double emi = (p * r * Math.pow(1 + r, n)) / (Math.pow(1 + r, n) - 1);
		return BigDecimal.valueOf(Math.round(emi * 100.0) / 100.0);
	}
	
	private LoanResponse mapToResponse(Loan loan) {
		int remaining = loan.getTenureMonths() - loan.getPaidMonths();
		BigDecimal totalPaid = loan.getEmiAmount()
				.multiply(BigDecimal.valueOf(loan.getPaidMonths()));
		BigDecimal remainingAmount = loan.getEmiAmount()
				.multiply(BigDecimal.valueOf(remaining));
		
		LoanResponse response = new LoanResponse();
		response.setLoanId(loan.getId());
		response.setLoanType(loan.getLoanType());
		response.setPrincipal(loan.getPrincipal());
		response.setInterestRate(loan.getInterestRate());
		response.setTenureMonths(loan.getTenureMonths());
		response.setEmiAmount(loan.getEmiAmount());
		response.setPaidMonths(loan.getPaidMonths());
		response.setRemianingMonths(remaining);
		response.setTotalPaidAmount(totalPaid);
		response.setRemainingAmount(remainingAmount);
		response.setAppliedOn(loan.getAppliedOn());
		response.setLoanStatus(loan.getLoanStatus());
		response.setApprovedOn(loan.getApprovedOn());
		
		return response;
	}
}
