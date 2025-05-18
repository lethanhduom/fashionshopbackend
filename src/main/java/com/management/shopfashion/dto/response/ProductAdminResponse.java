package com.management.shopfashion.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductAdminResponse {
private int id_product;
private int id_category;
private String nameProduct;
private String nameCategory;
private double rate;
private int isDelete;
private String productFor;

}
