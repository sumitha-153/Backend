package com.example.demo.CustomResponse;

import com.example.demo.Models.Meta;
//import com.example.demo.Models.User;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
//@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CustomResponse {
	
	private String message;
	private int statusCode;
	private Object data;
	private Meta meta;
	 
	
	
	public CustomResponse(String message, int statusCode, Object data) {
		super();
		this.message = message;
		this.statusCode = statusCode;
		this.data = data;
	}
	public CustomResponse(String message, int statusCode, Object data, Meta meta) {
		super();
		this.message = message;
		this.statusCode = statusCode;
		this.data = data;
		this.meta = meta;
	}
	public CustomResponse(String message, int statusCode) {
		super();
		this.message = message;
		this.statusCode = statusCode;
	}
	
	

}
