package com.amit.blog.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.amit.blog.entities.User;
import com.amit.blog.exceptions.ResoureNotFoundException;
import com.amit.blog.repo.UserRepo;
@Service
public class CustomUserDetailService implements UserDetailsService{
	@Autowired
	UserRepo userRepo;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		
		User user = this.userRepo.findByEmail(username).orElseThrow(() -> new ResoureNotFoundException("User ", " email : " + username, 0));

		return user;
	}

}