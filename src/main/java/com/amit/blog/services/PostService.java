package com.amit.blog.services;

import java.util.List;

import com.amit.blog.entities.Post;
import com.amit.blog.payloads.PostDto;
import com.amit.blog.payloads.PostResponse;

public interface PostService {

	PostDto createPost(PostDto postDto, Integer userId, Integer categoryId);
	PostDto updatePost(PostDto postDto, Integer postId);
     PostDto getPostById(Integer postId);
//	
	PostResponse getAllPosts(Integer pageNo, Integer pageSize, String sortBy);
	void deletePost(Integer postId);
	List<PostDto> getPostsByCategory(Integer categoryId);
    List<PostDto> getPostsByUser(Integer userId);
	List<PostDto> searchPosts(String keyWord);
}
