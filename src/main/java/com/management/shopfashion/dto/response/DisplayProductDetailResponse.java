package com.management.shopfashion.dto.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DisplayProductDetailResponse {
	private int id_product;
	private String nameProduct;
	private double price;
	private double rate;
	private int id_category;
	private List<String>images;
	private List<ProductDetailResponse>productDetailResponses;
}
