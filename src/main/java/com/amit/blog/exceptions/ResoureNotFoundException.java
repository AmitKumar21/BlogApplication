package com.amit.blog.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResoureNotFoundException extends RuntimeException{
	private String resourceName;
	private String fieldName;
	private long fieldValue;
	public ResoureNotFoundException(String resourceName, String fieldName, long fieldValue) {
		super(String.format("%s not found with %s : %d", resourceName,fieldName,fieldValue));
		this.resourceName = resourceName;
		this.fieldName = fieldName;
		this.fieldValue = fieldValue;
	}
	

}
