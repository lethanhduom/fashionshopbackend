package com.management.shopfashion.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import com.management.shopfashion.entity.Comment;


public interface CommentRepo extends JpaRepository<Comment,Integer> {
@Query("select c from Comment c where productDetail.product.id_product=?1")
public Page<Comment>getPageComment(int id,Pageable pageable);
@Query("select AVG(c.rate) from Comment c where productDetail.product.id_product=?1")
Double getAverageProduct(int id);
@Query("SELECT c.emotion, COUNT(c) FROM Comment c GROUP BY c.emotion")
List<Object[]> countByEmotion();
@Query("SELECT c.productDetail.product.id, c.emotion, c.productDetail.product.nameProduct, COUNT(c) FROM Comment c GROUP BY c.productDetail.product.id, c.emotion")
List<Object[]> countEmotionByProduct();

@Query("SELECT c.productDetail.product.id, c.productDetail.product.nameProduct, COUNT(c) " +
	       "FROM Comment c WHERE c.emotion = 0 " +  // 0 = tiêu cực
	       "GROUP BY c.productDetail.product.id, c.productDetail.product.nameProduct " +
	       "HAVING COUNT(c) >= :threshold")
	List<Object[]> findProductsWithNegativeComments(@Param("threshold") Long threshold);
	
	@Query("SELECT c FROM Comment c WHERE " +
		       "(:productId IS NULL OR c.productDetail.product.id = :productId) AND " +
		       "(:emotion IS NULL OR c.emotion = :emotion)")
		List<Comment> findCommentsByProductAndEmotion(@Param("productId") Integer productId,
		                                               @Param("emotion") Integer emotion);

}
