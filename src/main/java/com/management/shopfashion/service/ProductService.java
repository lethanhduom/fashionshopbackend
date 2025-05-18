package com.management.shopfashion.service;

import java.io.IOException;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.management.shopfashion.dto.ProductDetailDto;
import com.management.shopfashion.dto.ProductDto;
import com.management.shopfashion.dto.request.ProductRequest;
import com.management.shopfashion.dto.response.DisplayProductDetailResponse;
import com.management.shopfashion.dto.response.DisplayProductResponse;
import com.management.shopfashion.dto.response.ProductAdminResponse;
import com.management.shopfashion.entity.Product;

@Service
public interface ProductService {
	public Product createProduct(ProductRequest productRequest);
	public Page<DisplayProductResponse> displayProduct(int page,int size, String query);
	public DisplayProductDetailResponse displayProductDetail(int id);
	public void ChangeQuantityProductDetail(int id, int quantity);
	public Page<DisplayProductResponse>displayProductFollowCategory(int cate_id,int page, int size);
	public Page<ProductAdminResponse>getPageProductForAdmin(int page,int size,String query);
	public String actionProduct(int id);
	public void updateProduct(ProductDto productDto);
	public void updateProductDetail(ProductDetailDto productDetailDto);
	public Page<DisplayProductResponse>displayProductFollowPrductFor(int page,int size,String key);
	public void updateMissingEmbeddings();
	public List<DisplayProductResponse>getProductByImage(MultipartFile file)  throws IOException;
	public List<DisplayProductResponse> convertToDisplayResponse(List<Product> products);
	public Product getProductById(int id);
	public DisplayProductResponse convertProductDisplay(Product product) ;
	
}
