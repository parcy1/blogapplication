package com.springboot.blog.payloads;

import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.springboot.blog.entities.Role;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
public class UserDto {
	
	private int id;
	
	@NotNull
	@Size(min=4,message="User must be 4 characters!!")
	private String name;
	
	@Email(message="Email address not Valid!!")
	private String email;
	
	@NotEmpty()
	@Size(min=3,max=10,message="Password must be 3 or 10 charecters")
	private String password;
	
	@NotEmpty
	private String about;
	
	private Set<RoleDto>roles=new HashSet<>();
	
	
	
	

}
