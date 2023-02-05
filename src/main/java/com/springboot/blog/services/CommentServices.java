package com.springboot.blog.services;

import com.springboot.blog.payloads.CommentDto;

public interface CommentServices  {

	CommentDto createComment(CommentDto commentDto,Integer postId);
	void deleteComment(Integer commentId);
	

}
