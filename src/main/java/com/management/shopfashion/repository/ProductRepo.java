package com.management.shopfashion.repository;

import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.management.shopfashion.dto.response.DisplayProductResponse;
import com.management.shopfashion.entity.Product;

public interface ProductRepo extends JpaRepository<Product, Integer> {
 @Query("select p from Product p where p.isDelete=0")
 Page<Product>getListProduct(Pageable pageable);
 @Query(value = """
		    SELECT p.id_product, 
		           p.name_product, 
		           p.price, 
		           p.rate, 
		           (SELECT pi.image_url FROM product_image pi WHERE pi.id_product = p.id_product ORDER BY pi.id_product_image ASC LIMIT 1) AS ownerImage,
		           (SELECT GROUP_CONCAT(pi.image_url) FROM product_image pi WHERE pi.id_product= p.id_product) AS images
		    FROM product p WHERE p.is_delete = 0
		""", nativeQuery = true)
		Page<Object[]> displayProduct(Pageable pageable);
		@Query(value = """
			    SELECT p.id_product,
			           p.name_product,
			           p.price,
			           p.rate,
			           p.id_category,        
			           (SELECT GROUP_CONCAT(pi.image_url) 
			            FROM product_image pi 
			            WHERE pi.id_product = :productID) AS images,
			           JSON_ARRAYAGG(
			               JSON_OBJECT(
			                   'id_product_detail', pd.id_product_detail,
			                   'id_size', pd.id_size,
			                   'id_color', pd.id_color,
			                   'image_url', pi.image_url,
			                   'stock', pd.stock
			               )
			           ) AS productDetail
			    FROM product p
			    JOIN product_detail pd ON p.id_product = pd.id_product
			    JOIN product_image pi ON pi.id_product = p.id_product and pi.id_product_image = pd.id_product_image    
			    WHERE p.id_product = :productID
			    GROUP BY p.id_product, p.name_product, p.price, p.rate, p.id_category
			""", nativeQuery = true)
			Map<String, Object> displayProductDetail(@Param("productID") int productID);
		@Query ("select p from Product p where category.id_category=?1")
			Page<Product>getPageProductFollowCategory(int id,Pageable pageable);
		@Query ("select p from Product p where p.isDelete=0 and category.productFor=?1")
		Page<Product>getPageProductFollowProductFor(String key,Pageable pageable);
		@Query("Select p from Product p")
		Page<Product>getAllPageProduct(Pageable pageable);
		@Query("SELECT p FROM Product p WHERE p.isDelete=0 and LOWER(p.nameProduct) LIKE LOWER(CONCAT('%', :keyword, '%'))")
	    Page<Product> searchByName(@Param("keyword") String keyword, Pageable pageable);
		 
		
		@Query(value = """
			    SELECT 
			        p.id_product, 
			        p.name_product, 
			        p.price, 
			        p.rate, 
			        (SELECT pi.image_url 
			         FROM product_image pi 
			         WHERE pi.id_product = p.id_product 
			         ORDER BY pi.id_product_image ASC 
			         LIMIT 1) AS ownerImage,
			        (SELECT GROUP_CONCAT(pi.image_url) 
			         FROM product_image pi 
			         WHERE pi.id_product = p.id_product) AS images
			    FROM product p 
			    WHERE p.is_delete = 0
			      AND (:keyword IS NULL OR p.name_product LIKE CONCAT('%', :keyword, '%'))
			    """,
			    countQuery = """
			    SELECT COUNT(*) 
			    FROM product p 
			    WHERE p.is_delete = 0
			      AND (:keyword IS NULL OR p.name_product LIKE CONCAT('%', :keyword, '%'))
			    """,
			    nativeQuery = true)
			Page<Object[]> displayProductSearch(@Param("keyword") String keyword, Pageable pageable);

}
