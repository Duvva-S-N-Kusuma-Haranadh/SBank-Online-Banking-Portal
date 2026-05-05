package com.haranadh.SBank.Services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.haranadh.SBank.Entities.Locker;
import com.haranadh.SBank.Entities.User;
import com.haranadh.SBank.Repositories.LockerRepository;
import com.haranadh.SBank.Repositories.UserRepository;
import com.haranadh.SBank.dto.LockerResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LockerService {
	private final LockerRepository lockerRepository;
	private final UserRepository userRepository;
	
	public List<LockerResponse> getAvailableLockers() {
		return lockerRepository.findByStatus(Locker.LockerStatus.AVAILABLE)
				.stream()
				.map(this::mapToResponse)
				.collect(Collectors.toList());
	}
	
	public List<LockerResponse> getAvailableLockersByBranch(String branchName) {
		return lockerRepository.findByBranchNameAndStatus(branchName, Locker.LockerStatus.AVAILABLE)
				.stream()
				.map(this::mapToResponse)
				.collect(Collectors.toList());
	}
	
	public List<LockerResponse> getAvaialableLockersBySize(Locker.LockerSize size) {
		return lockerRepository.findByLockerSizeAndStatus(size, Locker.LockerStatus.AVAILABLE)
				.stream()
				.map(this::mapToResponse)
				.collect(Collectors.toList());
	}
	
	public LockerResponse getMyLocker(Long userId) {
		Locker locker = lockerRepository.findByUserId(userId)
				.orElseThrow(()->new RuntimeException("No locker assigned to this user"));
		
		return mapToResponse(locker);
	}
	
	public LockerResponse assignLocker(Long userId, String lockerNumber) {
		User user = userRepository.findById(userId)
				.orElseThrow(()->new RuntimeException("User not found"));
		
		if(lockerRepository.existsByUserId(userId)) {
			throw new RuntimeException("One user can only have one locker");
		}
		
		Locker locker = lockerRepository.findByLockerNumber(lockerNumber)
				.orElseThrow(()->new RuntimeException("No Locker has this number"));
		
		if(locker.getStatus() != Locker.LockerStatus.AVAILABLE) {
			throw new RuntimeException("Locker is not avaialble");
		}
		
		locker.setUser(user);
		locker.setStatus(Locker.LockerStatus.OCCUPIED);
		locker.setAssignedOn(LocalDateTime.now());
		
		Locker saved = lockerRepository.save(locker);
		return mapToResponse(saved);
	}
	
	public String releaseLocker(Long userId) {
		Locker locker = lockerRepository.findByUserId(userId)
				.orElseThrow(()->new RuntimeException("No locker found"));
		
		locker.setUser(null);
		locker.setStatus(Locker.LockerStatus.AVAILABLE);
		locker.setAssignedOn(null);
		
		lockerRepository.save(locker);
		return "Locker released successfully";
	}
	
	public long countAvailableLocker(String branchName) {
		return lockerRepository.countAvailableLockersByBranch(branchName);
	}
	
	public LockerResponse mapToResponse(Locker locker) {
		LockerResponse response = new LockerResponse();
		response.setLockerId(locker.getId());
		response.setLockerNumber(locker.getLockerNumber());
		response.setLockerSize(locker.getLockerSize().name());
		response.setBranchName(locker.getBranchName());
		response.setStatus(locker.getStatus().name());
		response.setAnnualRent(locker.getAnnualRent());
		response.setAssignedOn(locker.getAssignedOn());
		response.setAssignedToUser(
					locker.getUser() != null ? locker.getUser().getFullName():null
				);
		return response;
	}
}
