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
import org.springframework.web.bind.annotation.RestController;

import com.haranadh.SBank.Entities.Account;
import com.haranadh.SBank.Services.AccountService;
import com.haranadh.SBank.dto.AccountSummaryResponse;
import com.haranadh.SBank.dto.ApiResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/accounts")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class AccountController {
	private final AccountService accountService;

	@GetMapping("/user/{userId}")
	public ResponseEntity<ApiResponse<List<AccountSummaryResponse>>> getAccountsByUser(@PathVariable Long userId) {
		try {
			List<AccountSummaryResponse> accounts = accountService.getAccountsByUserId(userId);
			return ResponseEntity.ok(ApiResponse.success("Accounts fetched successfully", accounts));
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
		}
	}

	@GetMapping("/{accountNumber}")
	public ResponseEntity<ApiResponse<AccountSummaryResponse>> getAccountByNumber(@PathVariable String accountNumber) {
		try {
			AccountSummaryResponse account = accountService.getAccountByNumber(accountNumber);
			return ResponseEntity.ok(ApiResponse.success("Account fetched successfully", account));
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
		}
	}

	@GetMapping("/{accountNumber}/balance")
	public ResponseEntity<ApiResponse<BigDecimal>> getBalance(@PathVariable String accountNumber) {
		try {
			BigDecimal balance = accountService.getBalance(accountNumber);
			return ResponseEntity.ok(ApiResponse.success("Balance fetched successfully", balance));
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
		}
	}

	@GetMapping("/user/{userId}/total-balance")
	public ResponseEntity<ApiResponse<BigDecimal>> getTotalBalance(@PathVariable Long userId) {
		try {
			BigDecimal total = accountService.getTotalBalance(userId);
			return ResponseEntity.ok(ApiResponse.success("Total balance fetched", total));
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
		}
	}

	@PostMapping("/user/{userId}/create")
	public ResponseEntity<ApiResponse<AccountSummaryResponse>> createAccount(@PathVariable Long userId,
			@RequestBody Account account) {
		try {
			AccountSummaryResponse created = accountService.createAccount(userId, account);
			return ResponseEntity.ok(ApiResponse.success("Account created successfully", created));
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
		}
	}

	@PutMapping("/{accountNumber}/deativate")
	public ResponseEntity<ApiResponse<String>> deativateAccount(@PathVariable String accountNumber) {
		try {
			String message = accountService.deactivateAccount(accountNumber);
			return ResponseEntity.ok(ApiResponse.success(message));
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
		}
	}
}
