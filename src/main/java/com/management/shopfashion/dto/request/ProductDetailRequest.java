package com.management.shopfashion.dto.request;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class ProductDetailRequest {
	private int stock;
	private double price;
	private int isDelete;
	private int id_color;
	private int id_size;
	private MultipartFile image;


}
