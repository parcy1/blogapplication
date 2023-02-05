package com.springboot.blog.services;

import java.util.List;

import com.springboot.blog.entities.Post;
import com.springboot.blog.payloads.PostDto;
import com.springboot.blog.payloads.PostResponse;

public interface PostServices {

	
	// create
	
	PostDto createPost(PostDto postDto,Integer userId,Integer categoryId);
	
	//update
	
	PostDto updatePost(PostDto postDto,Integer postId);
	
	//
	void deletePost(Integer postId);
	
	
	//get all post
	
	PostResponse getAllPost(Integer pageNumber,Integer pageSize,String sortBy,String sortDir);
	
	//get sigle post
	
	PostDto getPostById(Integer postId);
	
	//get all post by categories
	
	List<PostDto> getPostsByCategory(Integer categoryId);
	
	
	//get all Post by User
	
	List<PostDto> getPostsByUser(Integer userId); 
	
	//search Posts
	
	List<PostDto>searchPosts (String keyword);
	
}
