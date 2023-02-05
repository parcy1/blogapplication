package com.springboot.blog.controllers;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.blog.payloads.ApiResponse;
import com.springboot.blog.payloads.UserDto;
import com.springboot.blog.services.UserServices;

@RestController
@RequestMapping("/api/users")
public class UserController {
	
	
	@Autowired
	private UserServices userService;
	
	//Post Create user
	@PostMapping("/")
	public ResponseEntity <UserDto> createUser(@Valid @RequestBody UserDto userDto)
	{
		
		UserDto createUserDto=this.userService.createUser(userDto);
		
		
		return new ResponseEntity<>(createUserDto,HttpStatus.CREATED);
		 
	}
	
	// Post Update User
	
	@PutMapping("/{userId}")
	public ResponseEntity <UserDto> updateUser(@Valid @RequestBody UserDto userDto,@PathVariable("userId") int uid)
	{
		UserDto updatedUser = this.userService.updateUser(userDto, uid);
		
		return ResponseEntity.ok(updatedUser);
	}
	
	@DeleteMapping("/{userId}")
	public ResponseEntity<ApiResponse>deleteUser(@PathVariable("userId")int uid)
	{
		this.userService.deleteUser(uid);
		return new ResponseEntity<ApiResponse>(new ApiResponse("User Deleted Successfully",true),HttpStatus.OK);
		
	}
	
	// Get User 
	
	@GetMapping("/")
	public ResponseEntity<List<UserDto>> getAllUsers()
	{
		return ResponseEntity.ok(this.userService.getAllUsers());
	}
	
	
	// Get User all 
	
		@GetMapping("/{userId}")
		public ResponseEntity<UserDto> getSingleUser(@PathVariable("userId")int uid)
		{
			return ResponseEntity.ok(this.userService.getUserById(uid));
		}

}
