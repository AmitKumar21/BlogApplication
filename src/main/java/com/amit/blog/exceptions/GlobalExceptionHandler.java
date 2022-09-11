package com.amit.blog.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.amit.blog.payloads.ApiResponse;

@ControllerAdvice
public class GlobalExceptionHandler {
	@ExceptionHandler(ResoureNotFoundException.class)
	public ResponseEntity<ApiResponse> resourceNotFoundExceptionHandler(ResoureNotFoundException exception){
		String messageString=exception.getMessage();
		ApiResponse response= new ApiResponse(messageString,false);
		return new ResponseEntity<ApiResponse>(response,HttpStatus.NOT_FOUND);
		
		
	}

}
