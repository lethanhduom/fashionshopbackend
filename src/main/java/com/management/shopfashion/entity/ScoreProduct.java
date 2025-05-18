package com.management.shopfashion.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ScoreProduct {
	 private Product product;
     private double score;
}
