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
@Table(name = "comment")
public class Comment {
	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	@Column(name = "id_comment")
	private int id_comment;
	@Column(name = "review",columnDefinition = "Longtext")
	private String review;
	@Column(name = "rate")
	private int rate;
	@Column(name="date_create")
	private String dateCreate;
	@Column(name="emotion")
	private int emotion; // 0 tiêu cực, 1 tích cực, 2 trung lập
	@ManyToOne
	@JoinColumn(name = "id_user")
	private User user;
	@ManyToOne
	@JoinColumn(name="id_product_detail")
	@Nullable
	private ProductDetail productDetail;
	

}
