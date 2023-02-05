package com.springboot.blog.payloads;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class PostResponse {
	
	
	private List<PostDto> content;
	private Integer pageNumber;
	private Integer pageSize;
	private long totalElement;
	private int totalPages;
	
	private boolean lastPage;

}
