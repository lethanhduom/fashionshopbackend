package com.management.shopfashion.dto.request;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductRequest {
	private String nameProduct;
	private String description;
	private double price;
	private String createTime;
	private double rate;
	private int isDelete;
	private int id_category;
	private List<ProductDetailRequest>details;
	
}
