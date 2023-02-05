package com.springboot.blog.services.impl;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.springboot.blog.exceptions.*;
import com.springboot.blog.config.AppConstants;
import com.springboot.blog.entities.Role;
import com.springboot.blog.entities.User;
import com.springboot.blog.payloads.UserDto;
import com.springboot.blog.repositories.RoleRepositories;
import com.springboot.blog.repositories.UserRepositories;
import com.springboot.blog.services.UserServices;


@Service
public class UserServiesImpl implements UserServices {
    
	@Autowired
	private UserRepositories userRepositories;
	
	@Autowired
	private ModelMapper modelMapper;
    
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private RoleRepositories roleRepositories;
	
	@Override
	public UserDto createUser(UserDto userDto) {
		
		 User user = this.dtoToUser(userDto);
		User savedUser = this.userRepositories.save(user);
		
		return this.userToDto(savedUser);
	}

	@Override
	public UserDto updateUser(UserDto userDto, Integer userId) {
		
		User user=this.userRepositories.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User","id",userId));
		
	
		user.setName(userDto.getName());
		user.setEmail(userDto.getEmail());
		user.setAbout(userDto.getAbout());
		user.setPassword(userDto.getPassword());
		
		    User updatedUser = this.userRepositories.save(user);
		    UserDto userDto1 = this.userToDto(updatedUser);
		return userDto1;
	}

	@Override
	public UserDto getUserById(Integer userId) {


		User user=this.userRepositories.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User","id",userId));
		
		
		return this.userToDto(user);
	}

	@Override
	public List<UserDto> getAllUsers() {


	      List<User> users = this.userRepositories.findAll();
	      
	      List<UserDto> userDtos = users.stream().map(user->this.userToDto(user)).collect(Collectors.toList());
	      
		return userDtos;
	}

	@Override
	public void deleteUser(Integer userId) {
		
		User user = this.userRepositories.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User","id",userId));
        
		this.userRepositories.delete(user);
	}
	
	public User dtoToUser(UserDto userDto)
	{
		User user=this.modelMapper.map(userDto, User.class);
		
		
//		user.setId(userDto.getId());
//		user.setName(userDto.getName());
//		user.setEmail(userDto.getEmail());
//		user.setAbout(userDto.getAbout());
//		user.setPassword(userDto.getPassword());
		return user;
	}
	
	public UserDto userToDto(User user)
	
	{
		UserDto userDto=this.modelMapper.map(user, UserDto.class);
		
//		userDto.setId(user.getId());
//		userDto.setName(user.getName());
//		userDto.setEmail(user.getEmail());
//		userDto.setAbout(user.getAbout());
//		userDto.setPassword(user.getPassword());
		
		return userDto;
		
	}

	@Override
	public UserDto registerNewUser(UserDto userDto) {


		User user = this.modelMapper.map(userDto, User.class);
		
		user.setPassword(this.passwordEncoder.encode(user.getPassword()));
		
		//role
		
		Role role = this.roleRepositories.findById(AppConstants.NORMAL_USER).get();
		
		user.getRoles().add(role);
		
		User newUser = this.userRepositories.save(user);
		
		return this.modelMapper.map(newUser, UserDto.class);
	}

}
