package com.management.shopfashion.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetailRequest {
	 private double price;
     private int quantity;
     private int id_product_detail;
     private int id_cart_item;
}
