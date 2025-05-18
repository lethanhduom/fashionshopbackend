package com.management.shopfashion.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.management.shopfashion.entity.productImage;

public interface ProductImageRepo extends JpaRepository<productImage, Integer> {
	  List<productImage> findByImageEmbeddingIsNull();
	  @Query("select pi from productImage pi where pi.imageEmbedding is not null and pi.product.isDelete=0")
	  List<productImage>getListImageProduct();
}
