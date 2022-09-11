package com.amit.blog.payloads;



import java.util.HashSet;

import java.util.Set;

import javax.validation.constraints.Email;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@NoArgsConstructor
public class UserDto {

	
	private int id;
	@NotEmpty(message = "Name is required")
	private String name;
	@NotEmpty(message = "Email must be valid")
    @Email
	private String email;
	@NotEmpty(message = "Password is required")
	@Size(min = 8 , max = 16 , message = " Password must be 8 char long and 16 max char")
	private String password;
	private String about;

	
private Set<RoleDto> roles = new HashSet<>();
	
	
	@JsonIgnore
	public String getPassword() {
		return this.password;
	}
	
	@JsonProperty
	public void setPassword(String password) {
		this.password=password;
	}
}
