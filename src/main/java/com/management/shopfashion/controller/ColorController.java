package com.management.shopfashion.controller;

import java.util.List;

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

import com.management.shopfashion.dto.ColorDto;
import com.management.shopfashion.dto.response.DisplayProductResponse;
import com.management.shopfashion.entity.Color;
import com.management.shopfashion.service.ColorService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/api/color")
public class ColorController {
	private ColorService colorService;
	@PostMapping("/create")
	public ResponseEntity<ColorDto>create(@RequestBody ColorDto color){
		return new ResponseEntity<>(colorService.createColor(color),HttpStatus.CREATED);
	}
	@PutMapping("/update")
	public ResponseEntity<ColorDto>update(@RequestBody ColorDto color){
		return ResponseEntity.ok(colorService.updateColor(color, color.getId_color()));
	}
	@GetMapping("/list")
	public ResponseEntity<List<ColorDto>>getList(){
		return ResponseEntity.ok(colorService.getListColor());
	}
	@GetMapping("/display")
	public Page<ColorDto>displayProduct(
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size){
		return colorService.getColorDisplay(page, size);
	}
	@GetMapping("/get-color")
	public ResponseEntity<ColorDto>getItem(@RequestParam int id){
		ColorDto colorDto=colorService.getItem(id);
		return ResponseEntity.ok(colorDto);
	}
}
