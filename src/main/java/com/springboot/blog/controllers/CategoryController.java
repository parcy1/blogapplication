package com.springboot.blog.controllers;

import java.util.List;

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
import com.springboot.blog.payloads.CategoryDto;
import com.springboot.blog.services.CategoryServices;

@RestController
@RequestMapping("api/categories")
public class CategoryController {
	
	@Autowired
	private CategoryServices categoryServices;
	// Create
	@PostMapping("/")
	public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto categoryDto)
	{
		CategoryDto createCategory = this.categoryServices.createCategory(categoryDto);
		
		return new ResponseEntity<CategoryDto>(createCategory,HttpStatus.CREATED);
	}
	
	//Update
	@PutMapping("/{catId}")
	public ResponseEntity<CategoryDto> updateCategory(@Valid @RequestBody CategoryDto categoryDto,@PathVariable Integer catId)
	{
		CategoryDto updatedCategory = this.categoryServices.updateCategory(categoryDto,catId);
		
		return new ResponseEntity<CategoryDto>(updatedCategory,HttpStatus.OK);
	}
	
	//Delete
	
	@DeleteMapping("/{catId}")
	public ResponseEntity<ApiResponse>deleteCategory(@PathVariable Integer catId)
	{
		this.categoryServices.deleteCategory(catId);
		return new ResponseEntity<ApiResponse>(new ApiResponse("Category Deleted Successfully",true),HttpStatus.OK);
		
	}
	// single category get
	@GetMapping("/{catId}")
	public ResponseEntity<CategoryDto>getCategory(@PathVariable Integer catId)
	{
		CategoryDto categoryDto = this.categoryServices.getCategory(catId);
		return new ResponseEntity<CategoryDto>(categoryDto,HttpStatus.OK);
		
	}
	
	// get All category
	
	@GetMapping("/")
	public ResponseEntity<List<CategoryDto>>getCategories()
	{
		List<CategoryDto> categories = this.categoryServices.getCategories();
		return ResponseEntity.ok(categories);
		
	}	

}
