package com.haranadh.SBank.Controllers;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.haranadh.SBank.Entities.Loan;
import com.haranadh.SBank.Services.LoanService;
import com.haranadh.SBank.dto.ApiResponse;
import com.haranadh.SBank.dto.LoanResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/loans")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class LoanController {
	private final LoanService loanService;

	@GetMapping("/user/{userId}")
	public ResponseEntity<ApiResponse<List<LoanResponse>>> getLoanByUserId(@PathVariable Long userId) {
		try {
			List<LoanResponse> loans = loanService.getLoansByUserId(userId);
			return ResponseEntity.ok(ApiResponse.success("Loans fetched successfully", loans));
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
		}
	}

	@GetMapping("/user/{userId}/status")
	public ResponseEntity<ApiResponse<List<LoanResponse>>> getLoansByStatus(@PathVariable Long userId,
			@RequestParam Loan.LoanStatus status) {
		try {
			List<LoanResponse> loans = loanService.getLoansByStatus(userId, status);
			return ResponseEntity.ok(ApiResponse.success("Loans fetched successfully", loans));
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
		}
	}

	@GetMapping("/{loanId}/user/{userId}")
	public ResponseEntity<ApiResponse<LoanResponse>> getLoanById(@PathVariable Long loanId, @PathVariable Long userId) {
		try {
			LoanResponse loan = loanService.getLoanById(loanId, userId);
			return ResponseEntity.ok(ApiResponse.success("Loan fetched succefully", loan));
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
		}
	}

	@PostMapping("/user/{userId}/apply")
	public ResponseEntity<ApiResponse<LoanResponse>> applyLoan(@PathVariable Long userId, @RequestBody Loan loan) {
		try {
			LoanResponse applied = loanService.applyLoan(userId, loan);
			return ResponseEntity.ok(ApiResponse.success("Loan application submitted", applied));
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
		}
	}

	@PutMapping("/{loanId}/approve")
	public ResponseEntity<ApiResponse<LoanResponse>> approveLoan(@PathVariable Long loanId) {
		try {
			LoanResponse approved = loanService.approvedLoan(loanId);
			return ResponseEntity.ok(ApiResponse.success("Loan approved successfully", approved));
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
		}
	}

	@PutMapping("/{loanId}/user/{userId}/pay-emi")
	public ResponseEntity<ApiResponse<LoanResponse>> payEmi(@PathVariable Long loanId, @PathVariable Long userId) {
		try {
			LoanResponse loan = loanService.payEmi(loanId, userId);
			return ResponseEntity.ok(ApiResponse.success("EMI paid successfully", loan));
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
		}
	}

	@GetMapping("/calculate-emi")
	public ResponseEntity<ApiResponse<BigDecimal>> calculateEmi(@RequestParam BigDecimal principal,
			@RequestParam BigDecimal interestRate, @RequestParam int tenureMonths) {
		try {
			BigDecimal emi = loanService.calculateEmi(principal, interestRate, tenureMonths);
			return ResponseEntity.ok(ApiResponse.success("EMI calaculates", emi));
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
		}
	}
}