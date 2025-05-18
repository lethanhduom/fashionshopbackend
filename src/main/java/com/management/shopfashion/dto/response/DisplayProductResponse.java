package com.management.shopfashion.dto.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DisplayProductResponse {
	
	private int id_product;
	private String nameProduct;
	private double price;
	private double rate;
	private String ownerImage;
	private List<String>images;
	
	
	
}
