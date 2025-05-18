package com.management.shopfashion.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDto {
	private int id_category;
	private String nameCategory;
	private String createTime;
	private int isDelete;
	private String productFor;
	private String imageUrl;
	
}
