package com.management.shopfashion.mapper;

import java.util.List;

import com.management.shopfashion.dto.response.DisplayProductResponse;
import com.management.shopfashion.dto.response.ProductAdminResponse;
import com.management.shopfashion.entity.Product;

public class ProductMapper {
	public static DisplayProductResponse MapDisplayProduct(Product product) {
		 String ownerImage = null;
		 List<String> imageUrls = null;
		 if (product.getProductImages() != null && !product.getProductImages().isEmpty()) {
		        ownerImage = product.getProductImages().get(0).getImageUrl(); 
		        imageUrls = product.getProductImages().stream()
		            .map(img -> img.getImageUrl()) 
		            .toList();
		    }
		  return new DisplayProductResponse(
			        product.getId_product(),
			        product.getNameProduct(),
			        product.getPrice(),
			        product.getRate(),
			        ownerImage,
			        imageUrls
			    );

	}
	public static ProductAdminResponse MapProductAdminResponse(Product product) {
	
		ProductAdminResponse productResponse=new ProductAdminResponse();
		productResponse.setId_product(product.getId_product());
		productResponse.setId_category(product.getCategory().getId_category());
		productResponse.setNameCategory(product.getCategory().getNameCategory());
		productResponse.setNameProduct(product.getNameProduct());
		productResponse.setRate(product.getRate());
		productResponse.setProductFor(product.getCategory().getProductFor());
		productResponse.setIsDelete(product.getIsDelete());
		return productResponse;
	}
}
