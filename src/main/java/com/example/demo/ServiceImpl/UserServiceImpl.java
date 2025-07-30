package com.example.demo.ServiceImpl;

import java.io.IOException;
import java.util.List;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.demo.CustomResponse.CustomResponse;
import com.example.demo.Models.Meta;
import com.example.demo.Models.User;
import com.example.demo.Repo.UserRepo;
import com.example.demo.Service.UserService;

import jakarta.servlet.http.HttpServletResponse;

@Service

public class UserServiceImpl implements UserService {

	@Autowired
	UserRepo userRepo;
	
	
	@Override
	public ResponseEntity<CustomResponse> fetchAllUser(int page, int size, String sortBy, String sortDir){
		Sort sort = sortDir.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
		Pageable page1 = PageRequest.of(page, size, sort);
		Page<User> pagedUser = userRepo.findAll(page1);
		Meta meta = new Meta();
		meta.setPage(pagedUser.getNumber());
		meta.setSize(pagedUser.getSize());
		meta.setTotalElements(pagedUser.getTotalElements());
		meta.setTotalPages(pagedUser.getTotalPages());
		CustomResponse res = new CustomResponse("Data fetched successfully", 200, pagedUser.getContent(), meta);

		return ResponseEntity.status(201).body(res);
	}

	@Override
	public ResponseEntity<CustomResponse> createUserWithPagination(int page, int size, String sortBy, String sortDir,
			User user) {
		Sort sort = sortDir.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
		Pageable page1 = PageRequest.of(page, size, sort);

		if(userRepo.existsByPhoneNumber(user.getPhoneNumber())) {
			CustomResponse res=new CustomResponse("Phone Number already exists",409);
			return ResponseEntity.status(HttpStatus.CONFLICT).body(res);
		}
		userRepo.save(user);
		Page<User> pagedUser = userRepo.findAll(page1);
		Meta meta = new Meta();
		meta.setPage(pagedUser.getNumber());
		meta.setSize(pagedUser.getSize());
		meta.setTotalElements(pagedUser.getTotalElements());
		meta.setTotalPages(pagedUser.getTotalPages());
		CustomResponse res = new CustomResponse("Data saved successfully", 201, pagedUser.getContent(), meta);

		return ResponseEntity.status(201).body(res);

	}

	@Override
	public ResponseEntity<CustomResponse> updateUser(long id, User user) {
		return userRepo.findById(id).map(items -> {
			items.setUserName(user.getUserName());
			items.setAge(user.getAge());
			items.setPhoneNumber(user.getPhoneNumber());
			items.setEmpNo(user.getEmpNo());
			User savedUser = userRepo.save(items);

			CustomResponse response = new CustomResponse("Data changed successfully", 200, savedUser);
			return ResponseEntity.status(200).body(response);
		}).orElse(null);
	}

	@Override
	public ResponseEntity<CustomResponse> excelDownload(HttpServletResponse response) throws IOException {
		Workbook wb = new XSSFWorkbook();
		Sheet sheet = wb.createSheet("User");

		Row headerRow = sheet.createRow(0);
		headerRow.createCell(0).setCellValue("User Id");
		headerRow.createCell(0).setCellValue("User Name");
		headerRow.createCell(0).setCellValue("User Age");
		headerRow.createCell(0).setCellValue("Employee No");

		int rowCount = 1;
		List<User> user = userRepo.findAll();
		for (User usr : user) {
			Row row = sheet.createRow(rowCount++);
			row.createCell(0).setCellValue(usr.getId());
			row.createCell(1).setCellValue(usr.getUserName());
			row.createCell(2).setCellValue(usr.getAge());
			row.createCell(3).setCellValue(usr.getEmpNo());

		}

		// Set response headers
		response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
		response.setHeader("Content-Disposition", "attachment; filename=user_data.xlsx");

		wb.write(response.getOutputStream());
		wb.close();

		CustomResponse res = new CustomResponse("Downloaded Successfully", 201);

		return ResponseEntity.status(201).body(res);

	}

}
