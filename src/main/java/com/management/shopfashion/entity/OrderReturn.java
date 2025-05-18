package com.management.shopfashion.entity;

import java.util.List;

import org.springframework.lang.Nullable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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
@Table(name = "order_return")
public class OrderReturn {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id_order_return")
private int id_order_return;
	@Column(name="total_price")
private double totalPrice;
	@Column(name="status")
private int status;
	@ManyToOne
	@JoinColumn(name = "id_user")
	private User  user;
	@OneToMany(mappedBy = "orderReturn")
	private List<OrderReturnDetail>orderReturnDetails;
	@ManyToOne
	@JoinColumn(name = "id_product_detail")
	@Nullable
	private ProductDetail productDetail;
}
