package com.springboot.blog.payloads;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.springboot.blog.entities.Category;
import com.springboot.blog.entities.Comment;
import com.springboot.blog.entities.User;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@NoArgsConstructor
public class PostDto {
	
	
	private Integer postId;
	
	private String title;
	
	private String content;
	
	private String imageName;
	
	private Date   addeddate;
	
	private CategoryDto category;
	
	private UserDto user;
	
	private Set<CommentDto> comments=new HashSet<>();
	

}
