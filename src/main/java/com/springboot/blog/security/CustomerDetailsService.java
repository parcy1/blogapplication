package com.springboot.blog.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.springboot.blog.entities.User;
import com.springboot.blog.exceptions.ResourceNotFoundException;
import com.springboot.blog.repositories.UserRepositories;

@Service
public class CustomerDetailsService implements UserDetailsService {
    
	@Autowired
	private UserRepositories userRepositories;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// Loding user from database by username
		
		User user = this.userRepositories.findByEmail(username).orElseThrow(()->new ResourceNotFoundException("User", "email :"+username, 0));
		return user;
	}

}
