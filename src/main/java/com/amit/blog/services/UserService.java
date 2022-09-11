package com.amit.blog.services;

import java.util.List;

import com.amit.blog.payloads.UserDto; 

public interface UserService {
	UserDto registerUser(UserDto userDto);
	UserDto createUser(UserDto user);
	UserDto updateUser(UserDto user, Integer userid);
	UserDto getUserById(Integer userid);
	
	List<UserDto> getAllUsers();
	void deleteUser(Integer userid);

}
