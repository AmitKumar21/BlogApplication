package com.amit.blog.services.impl;


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

import com.amit.blog.entities.Category;
import com.amit.blog.entities.Post;
import com.amit.blog.entities.User;
import com.amit.blog.exceptions.ResoureNotFoundException;
import com.amit.blog.payloads.PostDto;
import com.amit.blog.payloads.PostResponse;
import com.amit.blog.repo.CategoryRepo;
import com.amit.blog.repo.PostRepo;
import com.amit.blog.repo.UserRepo;
import com.amit.blog.services.PostService;
@Service
public class PostServiceImpl implements PostService {
	@Autowired
	PostRepo postRepo;
	@Autowired
	ModelMapper modelMapper;
	@Autowired
	private UserRepo userRepo;
	@Autowired
	CategoryRepo categoryRepo;
   
	public PostDto createPost(PostDto postDto, Integer userId, Integer categoryId) {
		
		// TODO Auto-generated method stub
		
		User user = userRepo.findById(userId).orElseThrow(()-> new ResoureNotFoundException("User", "id", userId));
		Category category = categoryRepo.findById(categoryId).orElseThrow(()-> new ResoureNotFoundException("Category", "id", categoryId));
				Post post= modelMapper.map(postDto, Post.class);
				post.setImageName("default.png");
				post.setAddedDate(new Date());
				post.setUser(user);
				post.setCategory(category);
				
		        Post createdpost= postRepo.save(post);
		return modelMapper.map(createdpost, PostDto.class);
	}

	@Override
	public List<PostDto> getPostsByCategory(Integer categoryId) {
		Category category = this.categoryRepo.findById(categoryId).orElseThrow(()-> new ResoureNotFoundException("Category", "id", categoryId));
		List<Post> posts= postRepo.findByCategory(category);
		
		System.out.print(posts);
		List<PostDto>  postDtos= posts.stream().map((post)-> this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		
		return postDtos;
	}

	@Override
	public List<PostDto> getPostsByUser(Integer userId) {
		// TODO Auto-generated method stub
		
		User user =this.userRepo.findById(userId).orElseThrow(()-> new ResoureNotFoundException("User", "id", userId));
		List<Post> posts= postRepo.findByUser(user);
   List<PostDto>  postDtos= posts.stream().map((post) ->this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		
		return postDtos;
	
	}

	@Override
	public PostDto getPostById(Integer postId) {
		
		Post post= postRepo.findById(postId).orElseThrow(()-> new ResoureNotFoundException("Post", "id", postId)); 
		return modelMapper.map(post, PostDto.class);
	}

	@Override
	public PostResponse getAllPosts(Integer pageNo, Integer pageSize,String sortBy) {
		
    Pageable pageable= PageRequest.of(pageNo, pageSize,Sort.by(sortBy).descending());
		Page<Post> pagepost= postRepo.findAll(pageable) ;
		List<Post> allposts= pagepost.getContent();
		 List<PostDto>  postDtos= allposts.stream().map((post) ->this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
			
		 PostResponse postResponse= new PostResponse();
		 postResponse.setContent(postDtos);
		 postResponse.setPageNumber(pagepost.getNumber());
		 postResponse.setPageSize(pagepost.getSize());
		 postResponse.setTotalPages(pagepost.getTotalPages());
		 postResponse.setTotalPost(pagepost.getTotalElements());
		return postResponse;
	}

	@Override
	public PostDto updatePost(PostDto postDto, Integer postId) {
		Post post= postRepo.findById(postId).orElseThrow(()-> new ResoureNotFoundException("Post", "id", postId)); 
        post.setContent(postDto.getContent());
        post.setTitle(postDto.getTitle());
        post.setImageName(postDto.getImageName());
        Post updatedpost= postRepo.save(post);
		return modelMapper.map(updatedpost, PostDto.class);
	}

	@Override
	public void deletePost(Integer postId) {
		Post post= postRepo.findById(postId).orElseThrow(()-> new ResoureNotFoundException("Post", "id", postId)); 
        postRepo.delete(post);
		
	}

	@Override
	public List<PostDto> searchPosts(String keyWord) {
		List<Post> allposts=postRepo.findByTitleContaining(keyWord);
		 List<PostDto>  postDtos= allposts.stream().map((post) ->this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
			
		return postDtos;
	}

	

	
}
