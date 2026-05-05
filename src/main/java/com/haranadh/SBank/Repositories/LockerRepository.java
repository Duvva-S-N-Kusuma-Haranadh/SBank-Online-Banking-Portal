package com.haranadh.SBank.Repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.haranadh.SBank.Entities.Locker;
import com.haranadh.SBank.Entities.Locker.LockerSize;
import com.haranadh.SBank.Entities.Locker.LockerStatus;

@Repository
public interface LockerRepository extends JpaRepository<Locker, Long> {
	Optional<Locker> findByLockerNumber(String lockerNumber);
	
	Optional<Locker> findByUserId(Long userId);
	
	List<Locker> findByStatus(Locker.LockerStatus status);
	
	List<Locker> findByBranchNameAndStatus(String branchName, Locker.LockerStatus status);
	
	List<Locker> findByLockerSizeAndStatus(LockerSize size, LockerStatus available);

	List<Locker> findByBranchNameAndLockerSizeAndStatus(String branchName, Locker.LockerSize lockerSize, Locker.LockerStatus status);
	
	List<Locker> findByBranchName(String branchName);
	
	boolean existsByUserId(Long userId);
	
	boolean existsByLockerNumber(String lockerNumber);
	
	@Query(value = "SELECT COUNT(l) FROM Locker l WHERE l.branchName = :branch AND l.status = 'AVAILABLE'", nativeQuery=true)
	long countAvailableLockersByBranch(@Param("branch") String branch);
	
	@Query(value="SELECT COUNT(l) FROM Locker l WHERE l.lockerSize = :size AND l.status = 'AVAILABLE'", nativeQuery=true)
	long countAvailableLockersBySize(@Param("size") Locker.LockerSize size);
	
	@Query(value="SELECT l FROM Locker l WHERE l.status = 'OCCUPIED' AND l.user IS NOT NULL", nativeQuery=true)
	List<Locker> findAllOccupiedLockers();
	
	@Query(value="SELECT l.status, COUNT(l) FROM Locker l GROUP BY l.status", nativeQuery=true)
	List<Object[]> countLockerByStatus();
}
