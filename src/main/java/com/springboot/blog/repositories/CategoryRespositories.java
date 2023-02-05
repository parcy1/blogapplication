package com.springboot.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springboot.blog.entities.Category;



public interface CategoryRespositories extends JpaRepository<Category, Integer>

{

	
}
