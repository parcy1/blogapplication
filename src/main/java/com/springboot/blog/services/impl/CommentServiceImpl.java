package com.springboot.blog.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springboot.blog.entities.Comment;
import com.springboot.blog.entities.Post;
import com.springboot.blog.exceptions.ResourceNotFoundException;
import com.springboot.blog.payloads.CommentDto;
import com.springboot.blog.repositories.CommentRepositories;
import com.springboot.blog.repositories.PostRepositories;
import com.springboot.blog.services.CommentServices;

@Service
public class CommentServiceImpl implements CommentServices {

	@Autowired
	private PostRepositories postRepositories;
	@Autowired
	private CommentRepositories commentRepositories;
	
	@Autowired
	private ModelMapper modelMapper;
	
	
	@Override
	public CommentDto createComment(CommentDto commentDto, Integer postId) {
		
		Post post = this.postRepositories.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post", "post id", postId));
		 
		Comment comment=this.modelMapper.map(commentDto, Comment.class);
		comment.setPost(post);
		Comment savedComment = this.commentRepositories.save(comment);
		return  this.modelMapper.map(savedComment, CommentDto.class);
	}

	@Override
	public void deleteComment(Integer commentId) {


		Comment com = this.commentRepositories.findById(commentId).orElseThrow(()->new ResourceNotFoundException("Comment","commentId", commentId));
       
		 this.commentRepositories.delete(com);
	}

}
