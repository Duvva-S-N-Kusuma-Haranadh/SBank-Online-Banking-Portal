package com.haranadh.SBank.Controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.haranadh.SBank.Entities.Locker;
import com.haranadh.SBank.Services.LockerService;
import com.haranadh.SBank.dto.ApiResponse;
import com.haranadh.SBank.dto.LockerResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/lockers")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class LockerController {
	private final LockerService lockerService;

	@GetMapping("/available")
	public ResponseEntity<ApiResponse<List<LockerResponse>>> getAvailableLockers() {
		try {
			List<LockerResponse> lockers = lockerService.getAvailableLockers();
			return ResponseEntity.ok(ApiResponse.success("Available lockers fetched", lockers));
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
		}
	}

	@GetMapping("/available/branch")
	public ResponseEntity<ApiResponse<List<LockerResponse>>> getByBranch(@RequestParam String branchName) {
		try {
			List<LockerResponse> lockers = lockerService.getAvailableLockersByBranch(branchName);
			return ResponseEntity.ok(ApiResponse.success("Lockers fetched by branch", lockers));
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
		}
	}

	@GetMapping("/available/size")
	public ResponseEntity<ApiResponse<List<LockerResponse>>> getBySize(@RequestParam Locker.LockerSize size) {
		try {
			List<LockerResponse> lockers = lockerService.getAvaialableLockersBySize(size);
			return ResponseEntity.ok(ApiResponse.success("Lockers fetched by size", lockers));
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
		}
	}

	@GetMapping("/user/{userId}")
	public ResponseEntity<ApiResponse<LockerResponse>> getMyLocker(@PathVariable Long userId) {
		try {
			LockerResponse locker = lockerService.getMyLocker(userId);
			return ResponseEntity.ok(ApiResponse.success("Your lokcer details", locker));
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
		}
	}

	@PostMapping("/assign")
	public ResponseEntity<ApiResponse<LockerResponse>> assignLocker(@RequestParam Long userId,
			@RequestParam String lockerNumber) {
		try {
			LockerResponse locker = lockerService.assignLocker(userId, lockerNumber);
			return ResponseEntity.ok(ApiResponse.success("Locker assigned successfully", locker));
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
		}
	}

	@PutMapping("user/{userId}/release")
	public ResponseEntity<ApiResponse<String>> releaseLocker(@PathVariable Long userId) {
		try {
			String message = lockerService.releaseLocker(userId);
			return ResponseEntity.ok(ApiResponse.success(message));
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
		}
	}

	@GetMapping("/count")
	public ResponseEntity<ApiResponse<Long>> countAvailable(@RequestParam String branchName) {
		try {
			long count = lockerService.countAvailableLocker(branchName);
			return ResponseEntity.ok(ApiResponse.success("Available locker count", count));
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
		}
	}
}
