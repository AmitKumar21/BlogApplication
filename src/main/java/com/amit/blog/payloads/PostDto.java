package com.amit.blog.payloads;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.amit.blog.entities.Category;
import com.amit.blog.entities.Comment;
import com.amit.blog.entities.User;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@NoArgsConstructor
public class PostDto {

	private String title;
	private Integer postId;
	private String content;
	private String imageName;
	private Date addedDate;

	private CategoryDto category;

	private UserDto user;
	private Set<CommentDto> comments=new HashSet<>();
}
