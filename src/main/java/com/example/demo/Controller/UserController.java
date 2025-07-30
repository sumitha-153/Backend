package com.example.demo.Controller;

import java.io.IOException;

//import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.CustomResponse.CustomResponse;
import com.example.demo.Models.User;
import com.example.demo.Repo.UserRepo;
import com.example.demo.Service.UserService;

import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/users")
public class UserController {
	
	@Autowired
	UserRepo userRepo;
	
	@Autowired
	UserService userService;
	
	@GetMapping
	public ResponseEntity<CustomResponse> fetchAllUsers(@RequestParam (defaultValue = "0") int page,
			@RequestParam(defaultValue = "3") int size,
			@RequestParam(defaultValue = "empNo") String sortBy,
			@RequestParam(defaultValue="asc") String sortDir) {
		return userService.fetchAllUser(page, size, sortBy, sortDir);
	}
	
	@PostMapping("/new/paged")
	public ResponseEntity<CustomResponse> createUser(@RequestParam (defaultValue = "0") int page,
			@RequestParam(defaultValue = "3") int size,
			@RequestParam(defaultValue = "empNo") String sortBy,
			@RequestParam(defaultValue="asc") String sortDir,			
			@RequestBody User user) {
		
		return userService.createUserWithPagination(page, size, sortBy, sortDir, user);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<CustomResponse> updateUser(@PathVariable Long id, @RequestBody User user){
		return userService.updateUser(id, user);
	}
	
	@GetMapping("/excel/download")
	public ResponseEntity<CustomResponse> excelDownload(HttpServletResponse response) throws IOException{
		return userService.excelDownload(response);
	}

	@DeleteMapping("/{id}")
	public void deletUser(@PathVariable long id) {
		userRepo.deleteById(id);
	}
	
	@GetMapping("/sample")
	public String Sample() {
		return "Hii...sumiii/";
	}
}
