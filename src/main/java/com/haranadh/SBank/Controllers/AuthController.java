package com.haranadh.SBank.Controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.haranadh.SBank.Entities.User;
import com.haranadh.SBank.Services.AuthService;
import com.haranadh.SBank.dto.ApiResponse;
import com.haranadh.SBank.dto.AuthResponse;
import com.haranadh.SBank.dto.LoginRequest;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class AuthController {
	private final AuthService authService;

	@PostMapping("/login")
	public ResponseEntity<ApiResponse<AuthResponse>> login(@RequestBody LoginRequest request) {
		try {
			AuthResponse response = authService.login(request);
			return ResponseEntity.ok(ApiResponse.success("Login successful", response));
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
		}
	}
	
	@PostMapping("/register")
	public ResponseEntity<ApiResponse<String>> register(@RequestBody User user) {
		try {
			String message = authService.registerUser(user);
			return ResponseEntity.ok(ApiResponse.success(message));
		} catch(Exception e) {
			return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
		}
	}
	
	@PutMapping("/change-password")
	public ResponseEntity<ApiResponse<String>> changePassword(@RequestParam String username, @RequestParam String oldPassword, @RequestParam String newPassword) {
		try {
			String message = authService.changePassword(username, oldPassword, newPassword);
			return ResponseEntity.ok(ApiResponse.success(message));
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
		}
	}
}
