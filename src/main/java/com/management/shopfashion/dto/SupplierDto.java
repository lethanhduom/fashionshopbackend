package com.management.shopfashion.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SupplierDto {
	private int id_supplier;
	private String nameSupplier;
	private String email;
	private String phoneNumber;
	private String address;
}
