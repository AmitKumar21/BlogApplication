package com.amit.blog.repo;


import org.springframework.data.jpa.repository.JpaRepository;

import com.amit.blog.entities.Comment;

public interface CommentRepo  extends JpaRepository<Comment	, Integer> {

}
