package com.management.shopfashion.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.management.shopfashion.entity.ProductDetail;

public interface ProductDetailRepo extends JpaRepository<ProductDetail, Integer> {
	@Query("Select p from ProductDetail p where p.id_product_detail=?1")
	public ProductDetail getProductDetailById(int id);
}
