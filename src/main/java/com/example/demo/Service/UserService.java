package com.example.demo.Service;

import java.io.IOException;

import org.springframework.http.ResponseEntity;

import com.example.demo.CustomResponse.CustomResponse;
import com.example.demo.Models.User;

import jakarta.servlet.http.HttpServletResponse;

public interface UserService {
	
	ResponseEntity<CustomResponse> createUserWithPagination (int page, int size, String sortBy, String sortDir,User user);
	ResponseEntity<CustomResponse> updateUser(long id, User user);
	ResponseEntity<CustomResponse> excelDownload(HttpServletResponse response) throws IOException;
	ResponseEntity<CustomResponse> fetchAllUser(int page, int size, String sortBy, String sortDir);

}
