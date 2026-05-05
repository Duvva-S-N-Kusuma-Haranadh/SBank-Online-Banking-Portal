package com.haranadh.SBank.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse<T> {
	private boolean success;
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public T getData() {
		return data;
	}
	public void setData(T data) {
		this.data = data;
	}
	public LocalDateTime getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}

	private String message;
	private T data;
	private LocalDateTime timestamp = LocalDateTime.now();
	
	public static <T> ApiResponse<T> success(String message, T data) {
		ApiResponse<T> response = new ApiResponse<>();
		response.setSuccess(true);
		response.setMessage(message);
		response.setData(data);
		response.setTimestamp(LocalDateTime.now());
		return response;
	}
	public static <T> ApiResponse<T> success(String message) {
		return success(message, null);
	}
	
	public static <T> ApiResponse<T> error(String message) {
		ApiResponse<T> response = new ApiResponse<>();
		response.setSuccess(false);
		response.setMessage(message);
		response.setTimestamp(LocalDateTime.now());
		return response;
	}
}
