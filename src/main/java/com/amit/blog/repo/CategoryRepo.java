package com.amit.blog.repo;



import org.springframework.data.jpa.repository.JpaRepository;

import com.amit.blog.entities.Category;


public interface CategoryRepo extends JpaRepository<Category, Integer>{
	
	
	
}