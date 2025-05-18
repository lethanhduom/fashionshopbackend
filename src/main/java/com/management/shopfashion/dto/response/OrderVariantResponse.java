package com.management.shopfashion.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderVariantResponse {
private int id_order_detail;
private double price;
private String image_url;
private int quantity;
private String nameProduct;
private String Color;
private String Size;
private int id_product_detail;
}
