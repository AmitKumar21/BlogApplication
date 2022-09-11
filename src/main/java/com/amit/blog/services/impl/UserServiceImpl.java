package com.amit.blog.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.amit.blog.config.AppConstants;
import com.amit.blog.entities.Role;
import com.amit.blog.entities.User;
import com.amit.blog.payloads.UserDto;
import com.amit.blog.repo.RoleRepo;
import com.amit.blog.repo.UserRepo;
import com.amit.blog.services.UserService;
import com.amit.blog.exceptions.ResoureNotFoundException;

@Service
public class UserServiceImpl implements UserService{
@Autowired	
  private UserRepo userRepo;

@Autowired
ModelMapper modelMapper;


@Autowired
private PasswordEncoder passwordEncoder;

@Autowired
private RoleRepo roleRepo;
	@Override
	public UserDto createUser(UserDto userdto) {
		// TODO Auto-generated method stub
		 User user = dtoToUser(userdto);
		 
		User savedUser= userRepo.save(user);
		
		
		
		return userToDto(savedUser);
	}

	@Override
	public UserDto updateUser(UserDto userDto, Integer userid) {

		User user = userRepo.findById(userid).orElseThrow(()-> new ResoureNotFoundException("User", "id", userid));
		//user.setId(userDto.getId());
		user.setName(userDto.getName());
		user.setEmail(userDto.getEmail());
		user.setPassword(userDto.getPassword());
		user.setAbout(userDto.getAbout());
		User updatedUser= userRepo.save(user);
		
		return userToDto(updatedUser);
	}

	@Override
	public UserDto getUserById(Integer userid) {
		// TODO Auto-generated method stub
		
		User user = userRepo.findById(userid).orElseThrow(()-> new ResoureNotFoundException("User", "id", userid));
		
		return userToDto(user);
	}

	@Override
	public List<UserDto> getAllUsers() {
		// TODO Auto-generated method stub
		
		List<User> users=  userRepo.findAll();
		
		List<UserDto> usersDto =users.stream().map(user->userToDto(user)).collect(Collectors.toList());
		return usersDto;
	}

	@Override
	public void deleteUser(Integer userid) {
		User user =userRepo.findById(userid).orElseThrow(()-> new ResoureNotFoundException("User", "id", userid));
		
          userRepo.delete(user);
		
	}
	
	private User dtoToUser(UserDto userDto) {
		User user = modelMapper.map(userDto, User.class);
		return user;
	}
	private UserDto userToDto(User user) {
		UserDto userDto= modelMapper.map(user, UserDto.class);
		
		
		return userDto;
	}

	@Override
	public UserDto registerUser(UserDto userDto) {

		User user = this.modelMapper.map(userDto, User.class);

		// encoded the password
		user.setPassword(this.passwordEncoder.encode(user.getPassword()));

		// roles
		Role role = this.roleRepo.findById(AppConstants.NORMAL_USER).get();

		user.getRoles().add(role);

		User newUser = this.userRepo.save(user);

		return this.modelMapper.map(newUser, UserDto.class);
	}
	
	
	
	
	

}
