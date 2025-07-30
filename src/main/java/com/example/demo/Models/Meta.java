package com.example.demo.Models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Meta {
	
	private int page;
	private int size;
	private long totalElements;
	private int totalPages;	

}
