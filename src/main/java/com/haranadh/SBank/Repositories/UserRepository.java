package com.haranadh.SBank.Repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.haranadh.SBank.Entities.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	Optional<User> findByUsername(String username);
	
	boolean existsByUsername(String username);
	
	boolean existsByEmail(String email);
	
	Optional<User> findByEmail(String email);
	
	Optional<User> findByPhone(String phone);
	
	List<User> findByIsActiveTrue();
	
	List<User> findByRole(User.Role role);
	
	List<User> findByFullNameContainingIgnoreCase(String name);
	
	Optional<User> findByUsernameAndIsActiveTrue(String username);
	
	@Query(value="Select u FROM User u LEFT JOIN FETCH u.accounts WHERE u.id = :id", nativeQuery=true)
	Optional<User> findByIdWithAccount(@Param("id") Long id);
	
	@Query(value="Select COUNT(u) FROM User u WHERE u.role = 'CUSTOMER'", nativeQuery=true)
	long countTotalCustomer();
}
