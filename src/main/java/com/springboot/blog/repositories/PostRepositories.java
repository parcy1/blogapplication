package com.springboot.blog.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springboot.blog.entities.Category;
import com.springboot.blog.entities.Post;
import com.springboot.blog.entities.User;

public interface PostRepositories extends JpaRepository<Post, Integer> {

	
	
	List<Post>findByUser(User user);
	List<Post>findByCategory(Category category);
	
	List<Post>findByTitleContaining(String title);
}
