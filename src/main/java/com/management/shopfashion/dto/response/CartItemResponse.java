package com.management.shopfashion.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CartItemResponse {
	public int id_cart_item;
	public int id_cart;
	public int quantity;
	public int id_product_detail;
	public String name;
	public String color;
	public double price;
	public String image;
	public String size;
	
}
