package com.management.shopfashion.entity;

import org.springframework.lang.Nullable;

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
@Table(name="cart_item")
public class CartItem {
	@Id
	@GeneratedValue(strategy =GenerationType.IDENTITY )
	@Column(name="id_cart_item")
	private int id_cart_item;
	@Column(name="quantity")
	private int quantity;
	@ManyToOne
	@JoinColumn(name = "id_cart")
	@Nullable
	private Cart cart;
	@ManyToOne
	@JoinColumn(name = "id_product_detail")
	@Nullable
	private ProductDetail productDetail;

}
