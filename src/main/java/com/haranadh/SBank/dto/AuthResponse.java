package com.haranadh.SBank.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponse {
	private String token;
	private String username;
	private String fullName;
	private String role;
	private String message;
	
	public AuthResponse(String token, String username, String fullName, String role) {
		this.token = token;
		this.username = username;
		this.fullName = fullName;
		this.role = role;
		this.message = "Login successful";
	}
}
