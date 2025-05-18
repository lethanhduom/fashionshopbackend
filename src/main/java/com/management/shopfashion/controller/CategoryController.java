package com.management.shopfashion.controller;

import java.util.List;

import org.aspectj.apache.bcel.classfile.Module.Require;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.management.shopfashion.dto.CategoryDto;
import com.management.shopfashion.dto.request.CategoryResquest;
import com.management.shopfashion.entity.Category;
import com.management.shopfashion.service.CategoryService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("api/category")
public class CategoryController {
	private CategoryService categoryService;
	@PostMapping("/create")
	public ResponseEntity<CategoryDto> createCategory(
			@RequestParam("nameCategory") String name,
			@RequestParam("productFor") String gender,
			@RequestParam("image") MultipartFile image

			){
		CategoryResquest categoryRequest=new CategoryResquest();
		categoryRequest.setImage(image);
		categoryRequest.setNameCategory(name);
		categoryRequest.setProductFor(gender);
		CategoryDto saveCategory=categoryService.createCategory(categoryRequest);
		return new ResponseEntity<>(saveCategory,HttpStatus.CREATED);
	}
	@PutMapping("/update-admin")
	public ResponseEntity<CategoryDto>update_category(
			@RequestParam ("id") int id,
			@RequestParam("nameCategory") String name,
			@RequestParam("productFor") String gender,
			@RequestParam( value= "image", required=false) MultipartFile image
			){
		CategoryResquest categoryRequest=new CategoryResquest();
		categoryRequest.setNameCategory(name);
		categoryRequest.setProductFor(gender);
		categoryRequest.setImage(image);
		return ResponseEntity.ok(categoryService.eidtCategoryAdmin(categoryRequest, id));
	}
	@PostMapping("/update")
	public ResponseEntity<CategoryDto>update(@RequestBody CategoryDto categoryDto){
		return ResponseEntity.ok(categoryService.updateCategory(categoryDto, categoryDto.getId_category()));
	}
	@GetMapping("/list")
	public ResponseEntity<List<CategoryDto>>getList(@RequestParam int isDelete){
		return ResponseEntity.ok(categoryService.getListCategory(isDelete));
	}
	@GetMapping("/page")
	public ResponseEntity<Page<CategoryDto>> getPage(
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size,
			@RequestParam(required =  false) String keyword
			){
		return ResponseEntity.ok(categoryService.getPagecategory(page, size,keyword));
	}
	@PutMapping("/action")
	public ResponseEntity<?> actionCategory(@RequestParam int id){
		categoryService.actionCategory(id);
		return ResponseEntity.ok().build();
	}
	@GetMapping("get-category")
	public ResponseEntity<CategoryDto>getCategory(@RequestParam int id){
		return ResponseEntity.ok(categoryService.getCategory(id));
	}
}
