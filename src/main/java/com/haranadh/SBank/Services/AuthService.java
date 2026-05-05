package com.haranadh.SBank.Services;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.haranadh.SBank.Entities.User;
import com.haranadh.SBank.Repositories.UserRepository;
import com.haranadh.SBank.dto.AuthResponse;
import com.haranadh.SBank.dto.LoginRequest;
import com.haranadh.SBank.util.AESUtil;
import com.haranadh.SBank.util.JwtUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {
	private final UserRepository userRepository;
	private final JwtUtil jwtUtil;
	private final AESUtil aesUtil;
	
	public AuthResponse login(LoginRequest request) throws Exception {
		String encryptedPassword = aesUtil.encrypt(request.getPassword());
		
		Optional<User> OptionalUser = userRepository.findByUsernameAndIsActiveTrue(request.getUsername());
		
		if(OptionalUser == null) {
			return null;
		}
		User user = OptionalUser.get();
		
		if(!encryptedPassword.equals(user.getPassword())) {
			throw new RuntimeException("Invalid credentials");
		}
		
		String token = jwtUtil.generateToken(user.getUsername());
		
		return new AuthResponse(token, user.getUsername(), user.getFullName(), user.getRole().name());
	}
	
	public String registerUser(User user) throws Exception {
		if(userRepository.existsByUsername(user.getUsername())) {
			throw new RuntimeException("Username already taken");
		}
		
		if(userRepository.existsByEmail(user.getEmail())) {
			throw new RuntimeException("Email already registered");
		}
		
		user.setPassword(aesUtil.encrypt(user.getPassword()));
		user.setRole(User.Role.CUSTOMER);
		user.setIsActive(true);
		
		userRepository.save(user);
		return "User registered successfully";
	}
	
	public String changePassword(String username, String oldPassword, String newPassword) throws Exception {
		String decryptedOld = aesUtil.decrypt(oldPassword);
		String decryptNew = aesUtil.decrypt(newPassword);
		
		User user = userRepository.findByUsername(username).orElseThrow(()-> new RuntimeException("User not found"));
		
		if(!decryptedOld.equals(user.getPassword())) {
			throw new RuntimeException("Old password is incorrect");
		}
		
		user.setPassword(decryptNew);
		userRepository.save(user);
		
		return "Password changes successfully";
	}
}