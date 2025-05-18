package com.management.shopfashion.entity;

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
@Table(name = "import_order_detail")
public class ImportOrderDetail {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id_import_order_detail")
	private int id_import_order_detail;
	@Column(name = "quantity")
	private int quantity;
	@Column(name = "price")
	private double price;
	@ManyToOne
	@JoinColumn(name = "id_import_order")
	private ImportOrder importOrder;
	@ManyToOne
	@JoinColumn(name = "id_product_detail")
	private ProductDetail productDetail;

}
