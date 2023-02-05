package com.springboot.blog.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
   
	@Autowired
	private UserDetailsService userDetailsService; 
	
	@Autowired
	private JwtTokenHelper jwtTokenHelper;
	
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		//get token
		
        String requestToken=request.getHeader("Authorization");
		
        // bearer 56447gsddf
        
        System.out.println(requestToken);
        
        String username=null;
        String token=null;
        if(requestToken != null && requestToken.startsWith("Bearer"))
        {
        	token = requestToken.substring(7);
        	
        	try{
        		username = this.jwtTokenHelper.getUsernameFromToken(token);
        		System.out.println("Username is : "+username);
			} catch(IllegalArgumentException e) {
				System.out.println("Unable to get jwt token");
			} catch (ExpiredJwtException e) {
        		System.out.println("Jwt Token has Expire");
			} catch (MalformedJwtException  e) {
				System.out.println("Invalid Jwt");
				
			}        	
        }else
        {
        	System.out.println("Jwt tokent does not start with bearer");
        }
        
         if (username != null && SecurityContextHolder.getContext() != null) {
        	 
        	 UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
        	 if(this.jwtTokenHelper.validateToken(token, userDetails)) {
				
        		 UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken=new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        		 usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        		 
        		 SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        		 
			}else
			{
				System.out.println("Invalid Jwt Token");
			}
			
		}else {
			System.out.println("User name is null or context is not null");
		}
         
         filterChain.doFilter(request, response);
	}

}
