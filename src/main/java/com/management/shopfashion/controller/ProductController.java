package com.management.shopfashion.controller;

import java.io.IOException;
import java.util.List;

import org.mapstruct.Mapping;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.management.shopfashion.dto.ProductDetailDto;
import com.management.shopfashion.dto.ProductDto;
import com.management.shopfashion.dto.request.ProductDetailRequest;
import com.management.shopfashion.dto.request.ProductRequest;
import com.management.shopfashion.dto.response.DisplayProductDetailResponse;
import com.management.shopfashion.dto.response.DisplayProductResponse;
import com.management.shopfashion.dto.response.ProductAdminResponse;
import com.management.shopfashion.entity.Product;
import com.management.shopfashion.service.ProductService;

import jakarta.websocket.server.PathParam;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/api/product")
public class ProductController {
	private ProductService productService;
	@PostMapping("/create")
	public ResponseEntity<Product> createProduct(@RequestParam("product") String productJson,@RequestParam("images") List<MultipartFile>images){
		int i=0;
		try {
			ObjectMapper objectMapper=new ObjectMapper();
			ProductRequest productRequest=objectMapper.readValue(productJson, ProductRequest.class);
		
		for(ProductDetailRequest detail:productRequest.getDetails()) {	
			detail.setImage(images.get(i));
			i++;
		}
		Product product=productService.createProduct(productRequest);
		return new ResponseEntity<>(product,HttpStatus.CREATED);
		}catch (Exception e) {
			e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
		
				
		
	}
	@GetMapping("/display")
	public Page<DisplayProductResponse>displayProduct(
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "12") int size,
			@RequestParam(required = false) String keyword
			){
		return productService.displayProduct(page, size,keyword);
	}
	@GetMapping("/detail/{id}")
	public ResponseEntity<DisplayProductDetailResponse> getDisplay(@PathVariable int id) {
		return ResponseEntity.ok(productService.displayProductDetail(id));
	}
	@GetMapping("/category-product")
	public ResponseEntity<Page<DisplayProductResponse>>getPageProductFollowCategory(
			@RequestParam int id_category,
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "12") int size
			){
		return ResponseEntity.ok(productService.displayProductFollowCategory(id_category, page, size));
	}
	@GetMapping("/display-admin")
	public ResponseEntity<Page<ProductAdminResponse>>getPageProductAdmin(
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size,
			@RequestParam (required = false) String keyword
			){
		return ResponseEntity.ok(productService.getPageProductForAdmin(page, size,keyword));
	}
	@PutMapping("/action")
	public ResponseEntity<String>actionProduct(@RequestParam int id){
		
		return ResponseEntity.ok(productService.actionProduct(id));
	}
	@PutMapping("/update-product")
	public ResponseEntity<?>updateProduct(@RequestBody ProductDto productDto){
		productService.updateProduct(productDto);
		return ResponseEntity.ok().build();
	}
	@PutMapping("/update-detail")
	public ResponseEntity<?>updateDetail(
			@RequestParam("id_product_detail") int id,
			@RequestParam("stock") int stock,
			@RequestParam("id_size") int id_size,
			@RequestParam("id_color") int id_color,
			@RequestParam("image") MultipartFile image
			){
		ProductDetailDto detailDto=new ProductDetailDto();
		detailDto.setId_color(id_color);
		detailDto.setStock(stock);
		detailDto.setId_size(id_size);
		detailDto.setId_product_detail(id);
		detailDto.setImage(image);
		productService.updateProductDetail(detailDto);
		return ResponseEntity.ok().build();
	}
	@GetMapping("/display-productfor")
	public ResponseEntity<Page<DisplayProductResponse>>DisplayProductFollowProductFor(
		
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "12") int size,
			@RequestParam String key
			){
		return ResponseEntity.ok(productService.displayProductFollowPrductFor(page, size, key));
	}
	@PostMapping("/update-miss-embedding")
	public ResponseEntity<String> updateMissEmbedding(){
		productService.updateMissingEmbeddings();
		return ResponseEntity.ok("Đã cập nhật các embedding cho các ảnh chưa có");
	}
	@PostMapping("/search-product-image")
	public ResponseEntity<List<DisplayProductResponse>>SearchProductImage(@RequestParam("file") MultipartFile file) throws IOException{
		List<DisplayProductResponse>getListProduct=productService.getProductByImage(file);
		return ResponseEntity.ok(getListProduct);
	}
}
