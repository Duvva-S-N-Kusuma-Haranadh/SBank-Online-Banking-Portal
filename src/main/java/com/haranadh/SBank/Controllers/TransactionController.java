package com.haranadh.SBank.Controllers;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.haranadh.SBank.Services.TransactionService;
import com.haranadh.SBank.dto.ApiResponse;
import com.haranadh.SBank.dto.BillPaymentRequest;
import com.haranadh.SBank.dto.TransactionResponse;
import com.haranadh.SBank.dto.TransferRequest;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/transactions")
@CrossOrigin(origins = "*")
public class TransactionController {
	private final TransactionService transactionService;

	@PostMapping("/transfer")
	public ResponseEntity<ApiResponse<TransactionResponse>> transferFunds(@RequestBody TransferRequest request) {
		try {
			TransactionResponse response = transactionService.transferFunds(request);

			return ResponseEntity.ok(ApiResponse.success("Transafer successful", response));
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
		}
	}

	@PostMapping("/bill-payment")
	public ResponseEntity<ApiResponse<TransactionResponse>> payBill(@RequestBody BillPaymentRequest request) {
		try {
			TransactionResponse response = transactionService.payBill(request);
			return ResponseEntity.ok(ApiResponse.success("Bill payment successful", response));
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
		}
	}

	@GetMapping("/account/{accountId}")
	public ResponseEntity<ApiResponse<List<TransactionResponse>>> getHistory(@PathVariable Long accountId) {
		try {
			List<TransactionResponse> history = transactionService.getTransactionHistory(accountId);

			return ResponseEntity.ok(ApiResponse.success("Transaction history fetched", history));

		} catch (Exception e) {
			return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
		}
	}

	@GetMapping("/account/{accountId}/recent")
	public ResponseEntity<ApiResponse<List<TransactionResponse>>> getRecentTransactions(@PathVariable Long accountId,
			@RequestParam(defaultValue = "5") Integer limit) {
		try {
			List<TransactionResponse> recent = transactionService.getRecentTransactions(accountId, limit);
			return ResponseEntity.ok(ApiResponse.success("Recent transactions fetched", recent));
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
		}
	}

	@GetMapping("/account/{accountId}/filter")
	public ResponseEntity<ApiResponse<List<TransactionResponse>>> getByDateRange(@PathVariable Long accountId,
			@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
			@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end) {
		try {
			List<TransactionResponse> filtered = transactionService.getTransactionsByDateRange(accountId, start, end);
			return ResponseEntity.ok(ApiResponse.success("Filtered transaction fetched", filtered));
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
		}
	}
}
