package com.springboot.blog.services;

import java.util.List;
import com.springboot.blog.entities.Category;
import com.springboot.blog.payloads.CategoryDto;

public interface CategoryServices {
	
	
	//Create
	CategoryDto createCategory(CategoryDto categoryDto);
	
	
	//update
	CategoryDto updateCategory(CategoryDto categoryDto,Integer categoryId);
	
	
	//Delete
	
	void deleteCategory(Integer categoryId);
	
	//Get
	
	 CategoryDto getCategory(Integer categoryId);
	
	//Get all
	 
	 List<CategoryDto> getCategories();

}
