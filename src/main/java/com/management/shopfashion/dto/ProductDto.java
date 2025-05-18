package com.management.shopfashion.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class ProductDto {
	private int id_product;
	private String nameProduct;
	private String description;
	private double price;
	private String createTime;
	private double rate;
	private int isDelete;
	private int id_category;
}
