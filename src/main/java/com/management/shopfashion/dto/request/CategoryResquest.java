package com.management.shopfashion.dto.request;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryResquest {
	private int id_category;
	private String nameCategory;
	private String createTime;
	private int isDelete;
	private String productFor;
	private MultipartFile image;
}
