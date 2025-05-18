package com.management.shopfashion.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CouponDto {
	private int id_coupon;
	private String code;
	private String typeCode;//percent && fixed
	private int decreasePercent;
	private long decreaseCrash;
	private double minOrderValue;
	private LocalDate startDate;
	private LocalDate endDate;
	private String category; // shipping & product
	private int isDelete;
}
