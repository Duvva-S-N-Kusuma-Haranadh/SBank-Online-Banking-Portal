package com.haranadh.SBank.Services;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.haranadh.SBank.Entities.Account;
import com.haranadh.SBank.Entities.Transaction;
import com.haranadh.SBank.Repositories.AccountRepository;
import com.haranadh.SBank.Repositories.TransactionRepository;
import com.haranadh.SBank.dto.BillPaymentRequest;
import com.haranadh.SBank.dto.TransactionResponse;
import com.haranadh.SBank.dto.TransferRequest;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TransactionService {
	private final TransactionRepository transactionRepository;
	private final AccountRepository accountRepository;

	@Transactional
	public TransactionResponse transferFunds(TransferRequest request) {
		if (request.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
			throw new RuntimeException("Transfer amount must be grater that zero.");
		}

		Account fromAccount = accountRepository.findByAccountNumberAndIsActiveTrue(request.getFromAccountNumber())
				.orElseThrow(() -> new RuntimeException("Sender account not found"));
		Account toAccount = accountRepository.findByAccountNumberAndIsActiveTrue(request.getToAccountNumber())
				.orElseThrow(() -> new RuntimeException("Receiver account not found"));

		if (fromAccount.getBalance().compareTo(request.getAmount()) < 0) {
			throw new RuntimeException("Insufficient balance");
		}

		fromAccount.setBalance(fromAccount.getBalance().subtract(request.getAmount()));
		accountRepository.save(toAccount);

		Transaction transaction = new Transaction();
		transaction.setFromAccount(fromAccount);
		transaction.setToAccount(toAccount);
		transaction.setAmount(request.getAmount());
		transaction.setTransactionType(Transaction.TransactionType.TRANSFER);
		transaction.setDescription(request.getDescription());
		transaction.setStatus(Transaction.TransactionStatus.SUCCESS);
		transaction.setTransactionDate(LocalDateTime.now());

		Transaction saved = transactionRepository.save(transaction);
		return mapToResponse(saved);
	}

	// Bill Payment
	@Transactional
	public TransactionResponse payBill(BillPaymentRequest request) {
		Account account = accountRepository.findByAccountNumberAndIsActiveTrue(request.getFromAccountNumber())
				.orElseThrow(() -> new RuntimeException("Account not found"));

		if (account.getBalance().compareTo(request.getAmount()) < 0) {
			throw new RuntimeException("Insufficient balance for bill payment");
		}

		account.setBalance(account.getBalance().subtract(request.getAmount()));
		accountRepository.save(account);

		Transaction transaction = new Transaction();
		transaction.setFromAccount(account);
		transaction.setToAccount(null);
		transaction.setAmount(request.getAmount());
		transaction.setTransactionType(Transaction.TransactionType.BILL_PAYMENT);
		transaction.setDescription(request.getBillType() + " bill - " + request.getBillNumber());
		;
		transaction.setStatus(Transaction.TransactionStatus.SUCCESS);
		transaction.setTransactionDate(LocalDateTime.now());

		Transaction saved = transactionRepository.save(transaction);
		return mapToResponse(saved);
	}

	public List<TransactionResponse> getTransactionHistory(Long accountId) {
		return transactionRepository.findAllByAccountId(accountId).stream().map(this::mapToResponse)
				.collect(Collectors.toList());
	}

	public List<TransactionResponse> getRecentTransactions(Long accountId, Integer limit) {
		return transactionRepository.findRecentTransactions(accountId, limit).stream().map(this::mapToResponse)
				.collect(Collectors.toList());
	}

	public List<TransactionResponse> getTransactionsByDateRange(Long accountId, LocalDateTime start,
			LocalDateTime end) {
		return transactionRepository.findByAccountIdAndDateRange(accountId, start, end).stream()
				.map(this::mapToResponse).collect(Collectors.toList());
	}

	private TransactionResponse mapToResponse(Transaction t) {
		TransactionResponse response = new TransactionResponse();
		response.setTransactionId(t.getId());
		response.setAmount(t.getAmount());
		response.setTransactionType(t.getTransactionType().name());
		response.setDescription(t.getDescription());
		response.setStatus(t.getStatus().name());
		response.setTransactionDate(t.getTransactionDate());
		response.setFromAccountNumber(t.getFromAccount() != null ? t.getFromAccount().getAccountNumber() : "N/A");
		response.setToAccountNumber(t.getToAccount() != null ? t.getToAccount().getAccountNumber() : "N/A");
		return response;
	}
}
