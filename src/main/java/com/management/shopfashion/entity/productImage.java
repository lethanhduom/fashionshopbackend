package com.management.shopfashion.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
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
@Table(name = "product_image")
public class productImage {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_product_image")
private int id_product_image;
	@Column(name = "image_url")
private String imageUrl;
	@Column (name="type_image")
private int typeImage;
	@Column(name="is_delete")
private int isDelete;
	 @Column(columnDefinition = "TEXT")
private String imageEmbedding; 
	@OneToOne(mappedBy = "productImage")
	private ProductDetail productDetail;
	@ManyToOne
	@JoinColumn(name="id_product")
	private Product product;
}
