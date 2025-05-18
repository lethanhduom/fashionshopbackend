package com.management.shopfashion.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CommentResponse {
private int rate;
private String username;
private int id_detail_product;
private String color;
private String size;
private String date;
private String review;
private int emotion;
private String namProduct;
}
