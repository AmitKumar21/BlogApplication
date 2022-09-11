package com.amit.blog.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amit.blog.entities.Category;
import com.amit.blog.exceptions.ResoureNotFoundException;
import com.amit.blog.payloads.CategoryDto;

import com.amit.blog.repo.CategoryRepo;

import com.amit.blog.services.CategoryService;
@Service
public class CategoryServiceImpl implements CategoryService{
	@Autowired
	CategoryRepo categoryRepo;

@Autowired
ModelMapper modelMapper;
	@Override
	public CategoryDto createCategory(CategoryDto categoryDto) {
	
	 Category category=modelMapper.map(categoryDto,Category.class);
		 
		Category savedCategory= categoryRepo.save(category);
		
		
		
		return modelMapper.map(savedCategory, CategoryDto.class);
	}

	@Override
	public CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryId) {

		Category category = categoryRepo.findById(categoryId).orElseThrow(()-> new ResoureNotFoundException("Category", "id", categoryId));
		//Category.setId(CategoryDto.getId());
		category.setCategoryDescription(categoryDto.getCategoryDescription());
		category.setCategoryTitle(categoryDto.getCategoryTitle());
		Category upadyeCategory= categoryRepo.save(category);
		return modelMapper.map(upadyeCategory, CategoryDto.class);
	}

	@Override
	public CategoryDto getCategoryById(Integer categoryId) {
		// TODO Auto-generated method stub
		
		Category category = categoryRepo.findById(categoryId).orElseThrow(()-> new ResoureNotFoundException("Category", "id", categoryId));
		
		return modelMapper.map(category, CategoryDto.class);
	}
    
	
	
	

	@Override
	public List<CategoryDto> getAllCategory() {
		// TODO Auto-generated method stub
		
		List<Category> categories=  categoryRepo.findAll();
		
		List<CategoryDto> categoriesDto =categories.stream().map(cat->CategoryToDto(cat)).collect(Collectors.toList());
		return categoriesDto;
	}

	@Override
	public void deleteCategory(Integer Categoryid) {
		Category Category =categoryRepo.findById(Categoryid).orElseThrow(()-> new ResoureNotFoundException("Category", "id", Categoryid));
		
          categoryRepo.delete(Category);
		
	}
	
	private Category dtoToCategory(CategoryDto categoryDto) {
		Category category = modelMapper.map(categoryDto, Category.class);
		return category;
	}
	private CategoryDto CategoryToDto(Category category) {
		CategoryDto categoryDto= modelMapper.map(category, CategoryDto.class);
		
		
		return categoryDto;
	}
	
	
	
	
}
