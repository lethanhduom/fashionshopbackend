package com.management.shopfashion.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.management.shopfashion.dto.ColorDto;
import com.management.shopfashion.dto.SizeDto;
import com.management.shopfashion.entity.Size;
import com.management.shopfashion.service.SizeService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/api/size")
public class SizeController {
	private SizeService sizeService;
	@PostMapping("/create")
	public ResponseEntity<SizeDto>create (@RequestBody SizeDto size){
		SizeDto createSize=sizeService.createSize(size);
		return new ResponseEntity<>(createSize,HttpStatus.CREATED);
	}
	@PostMapping("/update")
	public ResponseEntity<SizeDto>update(@RequestBody SizeDto size){
		SizeDto updateSize=sizeService.updateSize(size,size.getId_size() );
		return ResponseEntity.ok(updateSize);
	}
	@GetMapping("/list")
	public ResponseEntity<List<SizeDto>>getList(){
		return ResponseEntity.ok(sizeService.getListSize());
	}
	
	@GetMapping("/display")
	public Page<SizeDto>displayProduct(
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size){
		return sizeService.getPageSize(page, size);
	}
	@GetMapping("/get-size")
	public ResponseEntity<SizeDto>getSize(@RequestParam int id){
		return ResponseEntity.ok(sizeService.getSize(id));
	}

}
