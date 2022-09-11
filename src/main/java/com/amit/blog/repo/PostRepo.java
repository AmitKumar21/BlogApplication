package com.amit.blog.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.amit.blog.entities.Category;
import com.amit.blog.entities.Post;
import com.amit.blog.entities.User;

public interface PostRepo extends JpaRepository<Post, Integer>{

	List<Post> findByUser(User User);

	List<Post> findByCategory(Category category);
	
	List<Post> findByTitleContaining(String keyword);
	
}
