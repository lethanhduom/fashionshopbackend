package com.management.shopfashion.entity;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
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
@Table(name = "product")
public class Product {
	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	@Column(name = "id_product")
private int id_product;
	@Column(name = "name_product")
private String nameProduct;
	@Lob
	@Column(name = "description",columnDefinition = "Longtext")
private String description;
	@Column(name = "price")
private double price;
	@Column(name="create_time")
private String createTime;
	@Column(name = "rate")
private double rate;
	@Column(name="isDelete")
private int isDelete;
	@ManyToOne
	@JoinColumn(name="id_category")
	private Category category;
	@OneToMany(mappedBy = "product")
	private List<ProductDetail>productDetails;
	@OneToMany(mappedBy = "product")
	private List<WishList>wishLists;
	@OneToMany(mappedBy = "product")
	private List<productImage>productImages;
	
	
}


