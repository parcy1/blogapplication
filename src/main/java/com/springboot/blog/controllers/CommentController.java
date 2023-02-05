package com.springboot.blog.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.blog.payloads.ApiResponse;
import com.springboot.blog.payloads.CommentDto;
import com.springboot.blog.services.CommentServices;

@RestController
@RequestMapping("/api/")
public class CommentController {

	@Autowired
	private CommentServices commentServices;
	
	@PostMapping("/post/{postId}/comments")
	public ResponseEntity<CommentDto> createComment(@RequestBody CommentDto commentDto,@PathVariable Integer postId)
	{
		CommentDto createComment = this.commentServices.createComment(commentDto, postId);
		
		return new ResponseEntity<CommentDto>(createComment,HttpStatus.CREATED);
	}
	
	@DeleteMapping("/comments/{commentId}")
	public ResponseEntity<ApiResponse> deleteComment(@PathVariable Integer commentId)
	{
		this.commentServices.deleteComment(commentId);
		
		return new ResponseEntity<ApiResponse>(new ApiResponse("Comment Deleted  successfully", true),HttpStatus.OK);
	}
}
