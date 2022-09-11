package com.amit.blog.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;

import com.amit.blog.payloads.ApiResponse;
import com.amit.blog.payloads.UserDto;
import com.amit.blog.services.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {
	@Autowired
	UserService userService;

	@PostMapping("/")
	private ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto) {

		UserDto createdUserDto = userService.createUser(userDto);
		return new ResponseEntity<>(createdUserDto, HttpStatus.CREATED);

	}

	@PutMapping("/user/{userId}")
	private ResponseEntity<UserDto> updateUser(@Valid @RequestBody UserDto userDto, @PathVariable Integer userId) {

		UserDto updatedUserDto = userService.updateUser(userDto, userId);
		return ResponseEntity.ok(updatedUserDto);

	}
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/user/{userId}")
	public ResponseEntity<ApiResponse> deleteUser(@PathVariable Integer userId) {

		userService.deleteUser(userId);
		return new ResponseEntity(new ApiResponse("User Deleted Succesfully", true),HttpStatus.OK);

	}
	@GetMapping("/")
	private ResponseEntity<List<UserDto>> getAllUser() {

		List<UserDto> users=userService.getAllUsers();  
		return ResponseEntity.ok(users);

	}
	@GetMapping("/user/{userId}")
	private ResponseEntity<UserDto> getUserById(@PathVariable Integer userId) {

		UserDto updatedUserDto = userService.getUserById(userId);
		return ResponseEntity.ok(updatedUserDto);

	}
	
	 @ResponseStatus(code =HttpStatus.BAD_REQUEST)
	    @ExceptionHandler(MethodArgumentNotValidException.class)
	    public Map<String, String> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
	        Map<String, String> errors = new HashMap<>();
	     
	        ex.getBindingResult().getFieldErrors().forEach(error -> 
	            errors.put(error.getField(), error.getDefaultMessage()));
	         
	        return errors;
	    }
	

}
