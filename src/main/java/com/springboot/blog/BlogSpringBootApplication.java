package com.springboot.blog;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.springboot.blog.config.AppConstants;
import com.springboot.blog.entities.Role;
import com.springboot.blog.repositories.RoleRepositories;


@SpringBootApplication
public class BlogSpringBootApplication implements CommandLineRunner{
	
	@Autowired
	private PasswordEncoder passsworeEncoder;
	
	@Autowired
	private RoleRepositories roleRepositories;

	public static void main(String[] args) {
		SpringApplication.run(BlogSpringBootApplication.class, args);
	}
	
	@Bean
	public ModelMapper modelMapper()
	{
		return new ModelMapper();
	}

	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub
		System.out.println(this.passsworeEncoder.encode("sunil"));
		
		
		try {
			
			Role role=new Role();
			role.setId(AppConstants.NORMAL_USER);
			role.setName("NORMAL_USER");
			
			Role role1=new Role();
			role1.setId(AppConstants.NORMAL_USER);
			role1.setName("NORMAL_USER");
			List<Role> roles = List.of(role,role1);
			
			List<Role> Result = this.roleRepositories.saveAll(roles);
			Result.forEach(r->{
				System.out.println(r.getName());
			});
			
		} catch (Exception e) {
			
			e.printStackTrace();
			// TODO: handle exception
		}
	}

}
