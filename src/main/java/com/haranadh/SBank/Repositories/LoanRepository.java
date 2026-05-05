package com.haranadh.SBank.Repositories;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.haranadh.SBank.Entities.Loan;

@Repository
public interface LoanRepository extends JpaRepository<Loan, Long> {
	List<Loan> findByUserId(Long userId);
	
	List<Loan> findByUserIdAndLoanStatus(Long userId, Loan.LoanStatus loanType);
	
	List<Loan> findByLoanType(Loan.LoanType loanType);
	
	List<Loan> findByLoanStatus(Loan.LoanStatus loanStatus);
	
	List<Loan> findByUserIdAndLoanStatusNot(Long userId, Loan.LoanStatus loanStatus);
	
	Optional<Loan> findByIdAndUserId(Long loanId, Long userId);
	
	boolean existsByUserIdAndLoanTypeAndLoanStatus(Long userId, Loan.LoanType loanType, Loan.LoanStatus loanStatua);
	
	long countByUserId(Long userId);
	
	long countByUserIdAndLoanStatus(Long userId, Loan.LoanStatus loanStatus);
	
	@Query(value = "SELECT SUM(l.principal) FROM Loan l WHERE l.user.id = :userId AND l.loanStatus = :loanStatus", nativeQuery=true)
	Optional<BigDecimal> findTotalActiveLoanAmountUserId(@Param("userId") Long userId, @Param("loanStatus") Loan.LoanStatus loanStatus);
	
	List<Loan> findByPrincipalGreaterThan(BigDecimal amount);
	
	@Query(value="SELECT l FROM Loan l WHERE l.user.id = :userId AND l.paidMonths = l.tenureMonths", nativeQuery=true)
	List<Loan> findFullyPaidLoansByUserId(@Param("userid") Long userId);
}
