package com.haranadh.SBank.Services;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.haranadh.SBank.Entities.Account;
import com.haranadh.SBank.Entities.User;
import com.haranadh.SBank.Repositories.AccountRepository;
import com.haranadh.SBank.Repositories.UserRepository;
import com.haranadh.SBank.dto.AccountSummaryResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AccountService {
	private final AccountRepository accountRepository;
	private final UserRepository userRepository;

	public List<AccountSummaryResponse> getAccountsByUserId(Long userId) {
		List<Account> accounts = accountRepository.findByUserIdAndIsActiveTrue(userId);

		return accounts.stream().map(this::mapToResponse).collect(Collectors.toList());
	}

	public AccountSummaryResponse getAccountByNumber(String accountNumber) {
		Account account = accountRepository.findByAccountNumberAndIsActiveTrue(accountNumber)
				.orElseThrow(() -> new RuntimeException("Account not found"));

		return mapToResponse(account);
	}

	public BigDecimal getBalance(String accountNumber) {
		return accountRepository.findBalanceByAccountNumber(accountNumber)
				.orElseThrow(() -> new RuntimeException("Account not found"));
	}

	public BigDecimal getTotalBalance(Long userId) {
		return accountRepository.findTotalBalanceByUserId(userId).orElse(BigDecimal.ZERO);
	}

	public AccountSummaryResponse createAccount(Long userId, Account account) {
		User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

		if (accountRepository.existsByAccountNumber(account.getAccountNumber())) {
			throw new RuntimeException("Account number already exists");
		}

		account.setUser(user);
		account.setIsActive(true);
		account.setBalance(BigDecimal.ZERO);

		Account saved = accountRepository.save(account);
		return mapToResponse(saved);
	}

	public String deactivateAccount(String accountNumber) {
		Account account = accountRepository.findByAccountNumber(accountNumber)
				.orElseThrow(() -> new RuntimeException("Account not found"));

		account.setIsActive(false);
		accountRepository.save(account);

		return "Account deativated successfully";
	}

	private AccountSummaryResponse mapToResponse(Account account) {
		AccountSummaryResponse response = new AccountSummaryResponse();
		response.setAccountId(account.getId());
		response.setAccountNumber(account.getAccountNumber());
		response.setAccountType(account.getAccountType().name());
		response.setBalance(account.getBalance());
		response.setIfscCode(account.getIfscCode());
		response.setBranchName(account.getBranchName());
		response.setIsActive(account.getIsActive());
		response.setCreatedAt(account.getCreatedAt());
		response.setOwnerName(account.getUser().getFullName());
		response.setOwnerEmail(account.getUser().getEmail());
		return response;
	}
}
