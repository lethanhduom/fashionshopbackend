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
@Table(name = "category")
public class Category {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id_category")
private int id_category;
	@Column(name="name_cat")
private String nameCategory;
	@Column(name = "create_time")
private String createTime;
	@Column(name="product_for")
private String productFor;
	@Column(name = "is_delete")
private int isDelete;
	@Column(name="image_url")
private String imageUrl;
	@OneToMany(mappedBy = "category")
	private List<Product>products;
	
}
