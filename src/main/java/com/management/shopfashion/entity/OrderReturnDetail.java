package com.management.shopfashion.entity;

import org.springframework.lang.Nullable;

import jakarta.annotation.Generated;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "order_return_detail")
public class OrderReturnDetail {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id_order_return_detail")
private int id_order_return_detail;
	@Column(name="price")
private double price;
	@Column(name="quantity")
private int quantity;
	@ManyToOne
	@JoinColumn(name="id_order_return")
	private OrderReturn orderReturn;
	@ManyToOne
	@JoinColumn(name="id_product_detail")
	@Nullable
	private ProductDetail productDetail;
	

}
