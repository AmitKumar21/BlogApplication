package com.amit.blog.controllers;


import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.hibernate.engine.jdbc.StreamUtils;
import org.hibernate.event.spi.PostDeleteEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.amit.blog.payloads.ApiResponse;
import com.amit.blog.payloads.CategoryDto;
import com.amit.blog.payloads.ImageResponse;
import com.amit.blog.payloads.PostDto;
import com.amit.blog.payloads.PostResponse;
import com.amit.blog.payloads.UserDto;
import com.amit.blog.services.FileService;
import com.amit.blog.services.PostService;

@RestController
@RequestMapping("/api/v1/")
public class PostController {

	@Autowired
	PostService postService;
	@Autowired FileService fileService;
	@Value("${project.image}")
	String path;
	   @PostMapping("/user/{userId}/category/{categoryId}/posts")
	  public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto,
			  @PathVariable Integer userId, @PathVariable Integer categoryId){
		   PostDto createdPost= postService.createPost(postDto, userId, categoryId);
		   
		   
		   return new ResponseEntity<>(createdPost,HttpStatus.CREATED);
	   }
	   @GetMapping("/user/{userId}/posts")
	   public ResponseEntity<List<PostDto>> getPostByUser(@PathVariable Integer userId){
		   
		  List<PostDto> postDtos =postService.getPostsByUser(userId);
		  return new ResponseEntity<List<PostDto>>(postDtos,HttpStatus.OK);
	   }
	   @GetMapping("/category/{cateogryId}/posts")
	   public ResponseEntity<List<PostDto>> getPostByCategory(@PathVariable Integer cateogryId){
		   
		  List<PostDto> postDtos =postService.getPostsByCategory(cateogryId);
		  return new ResponseEntity<List<PostDto>>(postDtos,HttpStatus.OK);
	   }
	   
	   @GetMapping("/posts")
	   private ResponseEntity<PostResponse> getAllposts(
			   @RequestParam (value = "pageNo" , defaultValue = "0" , required = false) Integer pageNo,
			   @RequestParam(value = "pageSize",defaultValue = "10", required = false)Integer pageSize ,
			   @RequestParam(value = "sortBy",defaultValue = "postId", required = false)String sortBy) {

			PostResponse postResponse=postService.getAllPosts(pageNo,pageSize,sortBy) ; 
			
			return ResponseEntity.ok(postResponse);

		}
		@GetMapping("/post/{postId}")
		private ResponseEntity<PostDto> getUserById(@PathVariable Integer postId) {

			PostDto postdto = postService.getPostById(postId);
			return ResponseEntity.ok(postdto);

		}
		
		@PutMapping("/post/{postId}")
		private ResponseEntity<PostDto> updatePost( @RequestBody PostDto postDto, @PathVariable Integer postId) {
         PostDto updatePostDto= postService.updatePost(postDto, postId);
         return  ResponseEntity.ok(updatePostDto);
			

		}
		
		@DeleteMapping("/post/{postId}")
		private ResponseEntity<ApiResponse> deleteCategory(@PathVariable Integer postId) {

			postService.deletePost(postId);
			return new ResponseEntity(new ApiResponse("Post Deleted Succesfully", true),HttpStatus.OK);

		}
		@GetMapping("/post/search/{keyword}")
		private ResponseEntity<List<PostDto>> searchPost(@PathVariable String keyword) {

			List<PostDto> postDtos = postService.searchPosts(keyword);
			 return new ResponseEntity<List<PostDto>>(postDtos,HttpStatus.OK);

		}
		//post image upload
		
		
		@PostMapping("/post/image/upload/{postId}")
		public ResponseEntity<PostDto> uploadPostImage(
				@RequestParam("image") MultipartFile image ,
				@PathVariable  Integer postId
				) throws IOException{
			PostDto postdto = postService.getPostById(postId);
			
			String imagename= fileService.uploadImage(path, image);
			postdto.setImageName(imagename);
			  PostDto updatePostDto=  postService.updatePost(postdto, postId);
			return new ResponseEntity<PostDto>(updatePostDto,HttpStatus.OK);
			
		}
		
		@GetMapping("/post/image/{imageName}")
		public void downloadImage(
				@PathVariable("imageName") String imageName,
				HttpServletResponse response)throws IOException {
			
				InputStream resource=	fileService.getResource(path, imageName);
				response.setContentType(MediaType.IMAGE_JPEG_VALUE);
				response.setHeader("Content-Disposition", String.format("inline; filename=\"" + imageName + "\""));

				 //Here we have mentioned it to show as attachment
			//response.setHeader("Content-Disposition", String.format("attachment; filename=\"" + imageName + "\""));

				//response.setContentLength((int) file.length());

				FileCopyUtils.copy(resource, response.getOutputStream());
				
			
		}
		
		
}
