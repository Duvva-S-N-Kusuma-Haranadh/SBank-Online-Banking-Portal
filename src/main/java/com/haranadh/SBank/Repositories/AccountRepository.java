package com.haranadh.SBank.Repositories;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.haranadh.SBank.Entities.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
	Optional<Account> findByAccountNumber(String accountNumber);
	
	boolean existsByAccountNumber(String accountNumber);
	
	List<Account> findByUserId(Long userId);
	
	List<Account> findByUserIdAndIsActiveTrue(Long userId);

	List<Account> findByAccountType(Account.AccountType accountType);
	
	List<Account> findByBalanceGreaterThan(BigDecimal amount);
	
	List<Account> findByBalanceLessThan(BigDecimal amount);
	
	List<Account> findByBranchName(String branchName);
	
	Optional<Account> findByAccountNumberAndIsActiveTrue(String accountNumber);
	
	@Query("SELECT a.balance FROM Account a WHERE a.accountNumber = :accountNumber")
	Optional<BigDecimal> findBalanceByAccountNumber(@Param("accountNumber") String account);
	
	@Modifying
	@Transactional
	@Query("UPDATE Account a SET a.balance = :balance WHERE a.accountNumber = :accountNumber")
	int updateBalance(@Param("AccountNumber") String accountNumber, @Param("balance") BigDecimal balance);
	
	Optional<Account> findByUserIdAndAccountType(Long userId, Account.AccountType accountType);
	
	long countByUserId(Long userId);
	
	@Query("SELECT SUM(a.balance) FROM Account a WHERE a.user.id = :userId AND a.isActive = true")
	Optional<BigDecimal> findTotalBalanceByUserId(@Param("userId") Long userId);
}
