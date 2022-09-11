package com.amit.blog.payloads;

import javax.validation.constraints.NotEmpty;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CategoryDto {
	private Integer categoryId;
	@NotEmpty(message = "Title is required")
	private String categoryTitle;
	
	@NotEmpty(message = "Discription is required")
	private String categoryDescription;
}
