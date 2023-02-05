package com.springboot.blog.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.springboot.blog.exceptions.ResourceNotFoundException;
import com.springboot.blog.entities.Category;
import com.springboot.blog.entities.User;
import com.springboot.blog.payloads.CategoryDto;
import com.springboot.blog.payloads.UserDto;
import com.springboot.blog.repositories.CategoryRespositories;
import com.springboot.blog.services.CategoryServices;

@Service
public class CategoryServiceImpl implements CategoryServices{

	@Autowired
	private CategoryRespositories categoryRespositories;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Override
	public CategoryDto createCategory(CategoryDto categoryDto) {

        Category category = this.modelMapper.map(categoryDto, Category.class);
        Category addedcategory=this.categoryRespositories.save(category);
		return this.modelMapper.map(addedcategory, CategoryDto.class);
	}

	@Override
	public CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryId) {
		// TODO Auto-generated method stub
		
	Category cat =(Category) this.categoryRespositories.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category","Category Id",categoryId));
		
	    cat.setCategoryTitle(categoryDto.getCategoryTitle());
	    cat.setCategoryDescription(categoryDto.getCategoryDescription());
	    Category updatedcat= this.categoryRespositories.save(cat);
	    return this.modelMapper.map(updatedcat, CategoryDto.class);
	}

	@Override
	public void deleteCategory(Integer categoryId) {
		
		Category categoryRespositories = this.categoryRespositories.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category","Category id",categoryId));
		this.categoryRespositories.delete(categoryRespositories);
	}

	@Override
	public CategoryDto getCategory(Integer categoryId) {


		Category orElseThrow = this.categoryRespositories.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category","Category id",categoryId));
		return this.modelMapper.map(orElseThrow, CategoryDto.class);
	}

	@Override
	public List<CategoryDto> getCategories() {
		
		List<Category> findAll = this.categoryRespositories.findAll();
		List<CategoryDto> catDtos = findAll.stream().map((cat)->this.modelMapper.map(cat, CategoryDto.class)).collect(Collectors.toList());
		
		return catDtos;
	}
	
	
	
	
	

}
