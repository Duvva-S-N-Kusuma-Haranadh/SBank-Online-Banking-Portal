package com.haranadh.SBank;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import com.haranadh.SBank.dto.ApiResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {
	@ExceptionHandler(RuntimeException.class)
	public ResponseEntity<ApiResponse<String>> handleRuntimeException(RuntimeException ex) {
		return ResponseEntity
				.status(HttpStatus.BAD_REQUEST)
				.body(ApiResponse.error(ex.getMessage()));
	}
	
	@ExceptionHandler(jakarta.persistence.EntityNotFoundException.class)
	public ResponseEntity<ApiResponse<String>> handleEntityNotFounf(jakarta.persistence.EntityNotFoundException ex) {
		return ResponseEntity
				.status(HttpStatus.NOT_FOUND)
				.body(ApiResponse.error("Record not found: " + ex.getMessage()));
	}
	
	@ExceptionHandler(NoHandlerFoundException.class)
	public ResponseEntity<ApiResponse<String>> handleNoHandlerFound(NoHandlerFoundException ex) {
		return ResponseEntity
				.status(HttpStatus.NOT_FOUND)
				.body(ApiResponse.error("Endpoint not found: " + ex.getRequestURL()));
	}
	
	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<ApiResponse<String>> handleIllegalArgument(IllegalArgumentException ex) {
		return ResponseEntity
				.status(HttpStatus.BAD_REQUEST)
				.body(ApiResponse.error("Invalid input: "+ ex.getMessage()));
	}
	
	@ExceptionHandler(NullPointerException.class)
	public ResponseEntity<ApiResponse<String>> handleNullPointer(NullPointerException ex) {
		return ResponseEntity
				.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.body(ApiResponse.error("Something wen wrong. Pease try again later."));
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ApiResponse<String>> handleAllExceptions(Exception ex) {
		return ResponseEntity
				.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.body(ApiResponse.error("Server erro: "  + ex.getMessage()));
	}
}
