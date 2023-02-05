package com.springboot.blog.payloads;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class CategoryDto {
	
	private int categoryId;
	@NotBlank
    @Size(min=4,message="Minimum Must be atleast 4 character")
	private String categoryTitle;
	@NotBlank
    @Size(min=6,message="Minimum Must be atleast 4 character")
	private String categoryDescription;
	

}
