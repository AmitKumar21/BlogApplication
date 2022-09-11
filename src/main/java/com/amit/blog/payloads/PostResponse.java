package com.amit.blog.payloads;

import java.util.List;

import org.springframework.data.domain.jaxb.SpringDataJaxb.PageDto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@NoArgsConstructor
public class PostResponse {
 private List<PostDto> content;
 private int pageNumber;
 private int pageSize;
 private int totalPages;
 private long totalPost;
 private boolean islastPage;
}
