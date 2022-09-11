package com.amit.blog.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.amit.blog.payloads.ApiResponse;
import com.amit.blog.payloads.CategoryDto;
import com.amit.blog.services.CategoryService;


@RestController
@RequestMapping("/api/v1/categories")
public class CategoryController {
	@Autowired
   CategoryService categoryService;

	@PostMapping("/")
	private ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto categoryDto) {

		CategoryDto createdCategoryDto = categoryService.createCategory(categoryDto);
		return new ResponseEntity<>(createdCategoryDto, HttpStatus.CREATED);

	}

	@PutMapping("/category/{categoryId}")
	private ResponseEntity<CategoryDto> updateCategory(@Valid @RequestBody CategoryDto CategoryDto, @PathVariable Integer categoryId) {

		CategoryDto updatedCategoryDto = categoryService.updateCategory(CategoryDto, categoryId);
		return ResponseEntity.ok(updatedCategoryDto);

	}
	@DeleteMapping("/category/{categoryId}")
	private ResponseEntity<ApiResponse> deleteCategory(@PathVariable Integer categoryId) {

		categoryService.deleteCategory(categoryId);
		return new ResponseEntity(new ApiResponse("Category Deleted Succesfully", true),HttpStatus.OK);

	}
	@GetMapping("/")
	private ResponseEntity<List<CategoryDto>> getAllCategory() {

		List<CategoryDto> categorys=categoryService.getAllCategory();  
		return ResponseEntity.ok(categorys);

	}
	@GetMapping("/category/{categoryId}")
	private ResponseEntity<CategoryDto> getCategoryById(@PathVariable Integer categoryId) {

		CategoryDto updatedCategoryDto = categoryService.getCategoryById(categoryId);
		return ResponseEntity.ok(updatedCategoryDto);

	}
	
	 @ResponseStatus(code =HttpStatus.BAD_REQUEST)
	    @ExceptionHandler(MethodArgumentNotValidException.class)
	    public Map<String, String> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
	        Map<String, String> errors = new HashMap<>();
	     
	        ex.getBindingResult().getFieldErrors().forEach(error -> 
	            errors.put(error.getField(), error.getDefaultMessage()));
	         
	        return errors;
	    }
}
