package com.springboot.blog.services.impl;



import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.springboot.blog.entities.Category;
import com.springboot.blog.entities.Post;
import com.springboot.blog.entities.User;
import com.springboot.blog.exceptions.ResourceNotFoundException;
import com.springboot.blog.payloads.PostDto;
import com.springboot.blog.payloads.PostResponse;
import com.springboot.blog.repositories.CategoryRespositories;
import com.springboot.blog.repositories.PostRepositories;
import com.springboot.blog.repositories.UserRepositories;
import com.springboot.blog.services.PostServices;

@Service
public class PostServiceImpl implements PostServices {

	@Autowired
	private PostRepositories postRepositories;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private UserRepositories userRepositories;
	@Autowired
	private CategoryRespositories categoryRespositories;
	
	
	@Override
	public PostDto createPost(PostDto postDto,Integer userId,Integer categoryId) {
		
		User user=this.userRepositories.findById(userId).orElseThrow(()->new ResourceNotFoundException("User", "User id", userId));
		Category category=this.categoryRespositories.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category", "category id", categoryId));
		
		
		Post post = this.modelMapper.map(postDto, Post.class);
		post.setImageName("default.png");
		post.setAddedDate(new Date());
		post.setUser(user);
		post.setCategory(category);
		
		Post newPost = this.postRepositories.save(post);
		
		return this.modelMapper.map(newPost, PostDto.class);
	}

	@Override
	public PostDto updatePost(PostDto postDto, Integer postId) {

		Post post = this.postRepositories.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post", "post id", postId));
		 post.setTitle(postDto.getTitle());
		 post.setContent(postDto.getContent());
		 post.setImageName(postDto.getImageName());
		 Post updatedpost = this.postRepositories.save(post);
		 return this.modelMapper.map(updatedpost, PostDto.class);
	}

	@Override
	public void deletePost(Integer postId) {
		
		Post post = this.postRepositories.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post", "post id", postId));

		this.postRepositories.delete(post);	
	}

	@Override
	public PostResponse getAllPost(Integer pageNumber,Integer pageSize,String sortBy,String sortDir) {
		
		Sort sort=null;
		// one line code use Ternary Operator
		//Sort sort=(sortDir.equalsIgnoreCase("ASC"))?Sort.by(sortBy).ascending():Sort.by(sortBy).descending();
		if(sortDir.equalsIgnoreCase("ASC"))
		{
			sort=Sort.by(sortBy).ascending();
		}else
		{
			sort=Sort.by(sortBy).descending();
		}
		
		Pageable p= PageRequest.of(pageNumber, pageSize, sort);
		
		Page<Post> pagePost = this.postRepositories.findAll(p);
		List<Post> allposts = pagePost.getContent();
		
		 List<PostDto> postDtos = allposts.stream().map((post)->this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		 
		 PostResponse postResponse=new PostResponse();
		 postResponse.setContent(postDtos);
		 
		 postResponse.setPageNumber(pagePost.getNumber());
		 postResponse.setPageSize(pagePost.getSize());
		 postResponse.setTotalElement(pagePost.getTotalElements());
		 
		 postResponse.setTotalPages(pagePost.getTotalPages());
		 postResponse.setLastPage(pagePost.isLast());
		 
		return postResponse;
	}

	@Override
	public PostDto getPostById(Integer postId) {
		
		Post post = this.postRepositories.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post", "post id",postId));
		return this.modelMapper.map(post, PostDto.class);
	}

	@Override
	public List<PostDto> getPostsByCategory(Integer categoryId) {
	
		
		Category cat=this.categoryRespositories.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category", "category id", categoryId));
		List<Post> posts = this.postRepositories.findByCategory(cat);
		
		List<PostDto> postDtos = posts.stream().map((post)->this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		
		return postDtos;
	}

	@Override
	public List<PostDto> getPostsByUser(Integer userId) {
		
		User user=this.userRepositories.findById(userId).orElseThrow(()->new ResourceNotFoundException("User", "userId", userId));
		List<Post> posts = this.postRepositories.findByUser(user);
		
		List<PostDto> postDtos= posts.stream().map((post)->this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		
		return postDtos;
	}

	@Override
	public List<PostDto> searchPosts(String keyword) {

         List<Post> posts = this.postRepositories.findByTitleContaining(keyword);
         List<PostDto> PostDtos = posts.stream().map((post)->this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
         
		return PostDtos;
	}

}
