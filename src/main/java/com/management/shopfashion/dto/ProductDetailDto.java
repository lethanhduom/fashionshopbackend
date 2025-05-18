package com.management.shopfashion.dto;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductDetailDto {
	private int id_product_detail;
	private int stock;
//	private double price;
//	private int isDelete;
	private int id_size;
	private int id_color;
	private MultipartFile image;
}
