package com.haranadh.SBank.Repositories;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.haranadh.SBank.Entities.Transaction;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long>{
	List<Transaction> findByFromAccount_Id(Long id);
	
	List<Transaction> findByToAccount_Id(Long id);
	
	List<Transaction> findByStatus(Transaction.TransactionStatus status);
	
	List<Transaction> findByTransactionType(Transaction.TransactionType transactionType);
	
	List<Transaction> findByTransactionDateBetween(LocalDateTime start, LocalDateTime end);
	
	List<Transaction> findByAmountGreaterThan(BigDecimal amount);
	
	@Query(value="SELECT t FROM Transaction t WHERE t.fromAccount.id = :accountId OR t.toAccount.id = :accountId ORDER BY t.transactionDate DESC")
	List<Transaction> findAllByAccountId(@Param("accountId") Long accountId);
	
	@Query(value="SELECT t FROM Transaction t WHERE (t.fromAccount.id = :accountId OR t.toAccount.id = :accountId) AND t.transactionDate BETWEEN :start AND :end ORDER BY t.transactionDate DESC")
	List<Transaction> findByAccountIdAndDateRange(@Param("accountId") Long accountId, @Param("start") LocalDateTime start, @Param("end") LocalDateTime end);
	
	@Query(value="SELECT t FROM Transaction t WHERE t.fromAccount.id = :accountId OR t.toAccount.id = :accountId ORDER BY t.transactionDate DESC LIMIT :limit")
	List<Transaction> findRecentTransactions(@Param("accountId") Long accountId, @Param("limit") int limit);
	
	@Query(value="SELECT SUM(t.amount) FROM Transaction t WHERE t.fromAccount.id = :accountId AND t.status = :status")
	Optional<BigDecimal> findTotalSentByAccountId(@Param("accountId") Long accountId, @Param("status") Transaction.TransactionStatus status);
	
	@Query(value="SELECT SUM(t.amount) FROM Transaction t WHERE t.toAccount.id = :accountId AND t.status = :status")
	Optional<BigDecimal> findTotalReceivedByAccountId(@Param("accountId") Long accountId, @Param("status") Transaction.TransactionStatus status);
	
	long countByStatus(Transaction.TransactionStatus status);
}
