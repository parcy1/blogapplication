package com.springboot.blog.controllers;


import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.hibernate.engine.jdbc.StreamUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.springboot.blog.config.AppConstants;
import com.springboot.blog.entities.Post;
import com.springboot.blog.payloads.ApiResponse;
import com.springboot.blog.payloads.CategoryDto;
import com.springboot.blog.payloads.PostDto;
import com.springboot.blog.payloads.PostResponse;
import com.springboot.blog.services.FileService;
import com.springboot.blog.services.PostServices;

@RestController
@RequestMapping("/api/")
public class PostController {
	
	@Autowired
	private PostServices postServices;
	
	@Autowired
	private FileService fileService;
	
	@Value("${project.image}")
	private String path;
	// create
	
	@PostMapping("/user/{userId}/category/{categoryId}/posts")	
	public ResponseEntity<PostDto> createPost(
			@RequestBody PostDto postDto,
			@PathVariable Integer userId,
			@PathVariable Integer categoryId)
	{
		PostDto createdpost = this.postServices.createPost(postDto, userId, categoryId);
		
		return new ResponseEntity<PostDto>(createdpost,HttpStatus.CREATED);
	}
	
	// Get user
	
	@GetMapping("/user/{userId}/posts")
	public ResponseEntity<List<PostDto>> getPostsByUser(@PathVariable Integer userId)
	{
	    List<PostDto> posts=this.postServices.getPostsByUser(userId);
	    
	    return new ResponseEntity<List<PostDto>>(posts,HttpStatus.OK);
	}
	
	// get By category
	
	@GetMapping("/category/{categoryId}/posts")
	public ResponseEntity<List<PostDto>> getPostsByCategory(@PathVariable Integer categoryId)
	{
	    List<PostDto> posts=this.postServices.getPostsByCategory(categoryId);
	    
	    return new ResponseEntity<List<PostDto>>(posts,HttpStatus.OK);
	}
	
	//get by single post
	
	@GetMapping("/posts/{postId}")
	public ResponseEntity<PostDto>getPostById(@PathVariable Integer postId)
	{
		PostDto postDto = this.postServices.getPostById(postId);
		return new ResponseEntity<PostDto>(postDto,HttpStatus.OK);
		
	}
	//get By all Post
	
	@GetMapping("/posts")
	public ResponseEntity<PostResponse>getAllPost(
			@RequestParam(value="pageNumber",defaultValue = AppConstants.PAGE_NUMBER,required = false)Integer pageNumber
			,@RequestParam(value="pageSize",defaultValue = AppConstants.PAGE_SIZE,required = false)Integer pageSize,
			 @RequestParam(value="sortBy",defaultValue = AppConstants.SORT_BY,required = false)String sortBy,
			 @RequestParam(value="sortDir",defaultValue = AppConstants.SORT_DIR,required = false)String sortDir
			)
	{
		  PostResponse postResponse = this.postServices.getAllPost(pageNumber,pageSize,sortBy,sortDir);
		 return new ResponseEntity<PostResponse>(postResponse,HttpStatus.OK);
		
	}
	
	//delete post
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@DeleteMapping("/posts/{postId}")
	public ResponseEntity<ApiResponse>deletePost(@PathVariable Integer postId)
	{
		this.postServices.deletePost(postId);
		return new ResponseEntity<ApiResponse>(new ApiResponse("Post Deleted Successfully",true),HttpStatus.OK);
		
	}
	
	// update Post
	
	@PutMapping("/posts/{postId}")
	public ResponseEntity<PostDto>updatePost(@RequestBody PostDto postDto, @PathVariable Integer postId)
	{

      PostDto updatePost = this.postServices.updatePost(postDto, postId);
      
      return new ResponseEntity<PostDto>(updatePost,HttpStatus.OK);
		
	}
	
	// search
	@GetMapping("/posts/search/{keyword}")
	public ResponseEntity<List<PostDto>>searchPostByTitle(@PathVariable("keyword")String keyword)
	
	{
	    List<PostDto> result = this.postServices.searchPosts(keyword);
	    
	    return new ResponseEntity<List<PostDto>>(result,HttpStatus.OK);
	}
	
	//Post Image upload
	
	@PostMapping("/posts/image/upload/{postId}")
	public ResponseEntity<PostDto> uploadPostImage(
			@RequestParam("image")MultipartFile image,
			@PathVariable Integer postId) throws IOException{
	
		PostDto postDto = this.postServices.getPostById(postId);
		
		String fileName = this.fileService.uploadImage(path, image);
		
		postDto.setImageName(fileName);
		PostDto updatePost = this.postServices.updatePost(postDto, postId);
		
		return new ResponseEntity<PostDto>(updatePost,HttpStatus.OK);
	}
	
	
	// method to serve file
	@GetMapping(value="/post/image/{imageName}",produces = MediaType.IMAGE_JPEG_VALUE)
	public void downloadImage(@PathVariable("imageName") String imageName,HttpServletResponse response)throws IOException
	{
		InputStream resource =this.fileService.getResource(path, imageName);
		response.setContentType(MediaType.IMAGE_JPEG_VALUE);
		StreamUtils.copy(resource, response.getOutputStream());
		
	}

}
