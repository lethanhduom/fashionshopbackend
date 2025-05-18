package com.management.shopfashion.service.impl;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.management.shopfashion.config.MultipartInputStreamFileResource;
import com.management.shopfashion.dto.ProductDetailDto;
import com.management.shopfashion.dto.ProductDto;
import com.management.shopfashion.dto.request.ProductDetailRequest;
import com.management.shopfashion.dto.request.ProductRequest;
import com.management.shopfashion.dto.response.DisplayProductDetailResponse;
import com.management.shopfashion.dto.response.DisplayProductResponse;
import com.management.shopfashion.dto.response.ProductAdminResponse;
import com.management.shopfashion.dto.response.ProductDetailResponse;
import com.management.shopfashion.entity.Product;
import com.management.shopfashion.entity.ProductDetail;
import com.management.shopfashion.entity.ScoreProduct;
import com.management.shopfashion.entity.Size;
import com.management.shopfashion.entity.productImage;
import com.management.shopfashion.mapper.ProductMapper;
import com.management.shopfashion.repository.CategoryRepo;
import com.management.shopfashion.repository.ColorRepo;
import com.management.shopfashion.repository.CommentRepo;
import com.management.shopfashion.repository.ProductDetailRepo;
import com.management.shopfashion.repository.ProductImageRepo;
import com.management.shopfashion.repository.ProductRepo;
import com.management.shopfashion.repository.SizeRepo;
import com.management.shopfashion.service.CloudinaryService;
import com.management.shopfashion.service.ProductService;

import lombok.AllArgsConstructor;
import lombok.Data;

@Service
@AllArgsConstructor
public class ProductServiceImpl implements ProductService {
	private ProductRepo productRepo;
	private ProductImageRepo productImageRepo;
	private ProductDetailRepo productDetailRepo;
	private CategoryRepo categoryRepo;
	private CloudinaryService cloudinaryService;
	private ColorRepo colorRepo;
	private SizeRepo sizeRepo;
	private CommentRepo commentRepo;
	private final RestTemplate restTemplate = new RestTemplate();
    private final String FASTAPI_URL = "http://localhost:8000/image-embedding-from-url/";
    ObjectMapper objectMapper = new ObjectMapper();
	@Override
	public Product createProduct(ProductRequest productRequest) {
	Product product=new Product();
	product.setNameProduct(productRequest.getNameProduct());
	product.setPrice(productRequest.getPrice());
	product.setDescription(productRequest.getDescription());
	product.setCreateTime(productRequest.getCreateTime());
	product.setRate(productRequest.getRate());
	product.setIsDelete(productRequest.getIsDelete());
	product.setCategory(categoryRepo.findById_category(productRequest.getId_category()));
	productRepo.save(product);
	 
	List<productImage>productImage=new ArrayList<>();
	for(ProductDetailRequest productDetailRequest:productRequest.getDetails()) {
		ProductDetail productDetail=new ProductDetail();
		productDetail.setColor(colorRepo.findByIdColor(productDetailRequest.getId_color()));
		productDetail.setSize(sizeRepo.findSizeById(productDetailRequest.getId_size()));
		productDetail.setStock(productDetailRequest.getStock());
		productDetail.setIsDelete(0);
		productDetail.setProduct(product);
		 
		if(productDetailRequest.getImage()!=null) {
			try {
				String url=cloudinaryService.uploadImage(productDetailRequest.getImage());
				productImage image=new productImage();
				image.setImageUrl(url);
				image.setProduct(product);
				productDetail.setProductImage(image);
				productImage.add(image);
			} catch (IOException e) {
				
				e.printStackTrace();
			}
		}
		productDetailRepo.save(productDetail);
		
	}
;
		product.setProductImages(productImage);
		return productRepo.save(product);
	}
	@Override
	public Page<DisplayProductResponse> displayProduct(int page, int size, String query) {
		Page<Object[]>result=null;
		if(query==null||query.trim()=="") {
			result=productRepo.displayProduct(PageRequest.of(page, size));
		}else {
			result= productRepo.displayProductSearch(query, PageRequest.of(page, size));
		}
	
		return result.map(obj->new DisplayProductResponse(
				(int)obj[0],
				(String)obj[1],
				(double)obj[2],
				commentRepo.getAverageProduct((int)obj[0])==null?0:commentRepo.getAverageProduct((int)obj[0]),
				(String)obj[4],
				 obj[5] != null ? Arrays.asList(((String) obj[5]).split(",")) : new ArrayList<>()		
				));
	}
	@Override
	public DisplayProductDetailResponse displayProductDetail(int id) {
		Map<String, Object>result=productRepo.displayProductDetail(id);
		if(result==null||result.isEmpty()) {
			throw new RuntimeException("product not found");
		}
		ObjectMapper objectMapper=new ObjectMapper();
		List<ProductDetailResponse>productDetails=new ArrayList<>();
		try {
			productDetails=objectMapper.readValue(result.get("ProductDetail").toString(),
					 new TypeReference<List<ProductDetailResponse>>() {}
					);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return new DisplayProductDetailResponse(
				(int)result.get("id_product"),
				(String)result.get("name_product"),
				(double)result.get("price"), 
				commentRepo.getAverageProduct((int)result.get("id_product"))==null?0:commentRepo.getAverageProduct((int)result.get("id_product")),
				 (int) result.get("id_category"),
				Arrays.asList(((String) result.get("images")).split(",")), 
				productDetails);
	}
	@Override
	public void ChangeQuantityProductDetail(int id, int quantity) {
		ProductDetail getProductDetail=productDetailRepo.getProductDetailById(id);
		getProductDetail.setStock(getProductDetail.getStock()-quantity);
		
	}
	@Override
	public Page<DisplayProductResponse> displayProductFollowCategory(int cate_id, int page, int size) {
	Page<Product>getPageProduct=productRepo.getPageProductFollowCategory(cate_id, PageRequest.of(page, size));
		return getPageProduct.map(product->ProductMapper.MapDisplayProduct(product));
	}
	
	@Override
	public Page<DisplayProductResponse> displayProductFollowPrductFor(int page, int size, String key) {
		Page<Product>getPageProduct=productRepo.getPageProductFollowProductFor(key, PageRequest.of(page, size));
		return getPageProduct.map(product->ProductMapper.MapDisplayProduct(product));
	}
	@Override
	public Page<ProductAdminResponse> getPageProductForAdmin(int page, int size,String query) {
		Page<Product>getListProduct=null;
		if(query==null||query.trim()=="") {
			getListProduct=productRepo.getAllPageProduct(PageRequest.of(page, size));
		}else {
			getListProduct=productRepo.searchByName(query, PageRequest.of(page, size));
		}
	
				return getListProduct.map(product->ProductMapper.MapProductAdminResponse(product));
	}
	@Override
	public String actionProduct(int id) {
		Product product=productRepo.getById(id);
		if(product.getCategory().getIsDelete()==1) {
			return "Category has been block! You can't act this product";
		}else {
			int isDelete=product.getIsDelete()==0?1:0;
			product.setIsDelete(isDelete);
			productRepo.save(product);
			return "success";
		}
		
		
	}
	@Override
	public void updateProduct(ProductDto productDto) {
	Product product=productRepo.getById(productDto.getId_product());
	product.setNameProduct(productDto.getNameProduct());
	product.setDescription(productDto.getDescription());
	product.setPrice(productDto.getPrice());
	product.setCategory(categoryRepo.findById_category(productDto.getId_category()));
	productRepo.save(product);
	}
	@Override
	public void updateProductDetail(ProductDetailDto productDetailDto) {
		ProductDetail productDetail=productDetailRepo.getById(productDetailDto.getId_product_detail());
		productDetail.setStock(productDetailDto.getStock());
		productDetail.setColor(colorRepo.getById(productDetailDto.getId_color()));
		productDetail.setSize(sizeRepo.findSizeById(productDetailDto.getId_size()));
		if(productDetailDto.getImage()!=null) {
			try {
				String url=cloudinaryService.uploadImage(productDetailDto.getImage());
				
				productImage productImage=productImageRepo.getById(productDetail.getProductImage().getId_product_image());
				productImage.setImageUrl(url);
				productImageRepo.save(productImage);
				productDetail.setProductImage(productImage);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		productDetailRepo.save(productDetail);
		
	}
	@Override
	public void updateMissingEmbeddings() {
		  List<productImage> images = productImageRepo.findByImageEmbeddingIsNull();

	        for (productImage image : images) {
	            try {
	                URI uri = UriComponentsBuilder.fromHttpUrl(FASTAPI_URL)
	                        .queryParam("image_url", image.getImageUrl())
	                        .build().toUri();

	                // FastAPI trả về Set, nên map lại để lấy embedding
	                Map<String, Object> response = restTemplate.postForObject(uri, null, Map.class);
	                if (response != null && !response.containsKey("error")) {
	                    Object embeddingObj = response.values().iterator().next();
	                    String embeddingJson = new ObjectMapper().writeValueAsString(embeddingObj);

	                    image.setImageEmbedding(embeddingJson);
	                    productImageRepo.save(image);
	                } else {
	                    System.out.println("Lỗi khi gọi API cho imageId = " + image.getId_product_image() + ": " + response.get("error"));
	                }
	            } catch (Exception e) {
	                System.out.println("Exception khi xử lý imageId = " + image.getId_product_image());
	                e.printStackTrace();
	            }
	        }
	}
	
	 private double cosineSimilarity(List<Double> v1, List<Double> v2) {
	        double dot = 0.0, normA = 0.0, normB = 0.0;
	        for (int i = 0; i < v1.size(); i++) {
	            dot += v1.get(i) * v2.get(i);
	            normA += Math.pow(v1.get(i), 2);
	            normB += Math.pow(v2.get(i), 2);
	        }
	        return dot / (Math.sqrt(normA) * Math.sqrt(normB));
	    }
	@Override
	public List<DisplayProductResponse> getProductByImage(MultipartFile file)  throws IOException {
	     List<Double> queryEmbedding = getImageEmbedding(file);

	        // Lấy toàn bộ image từ DB và so sánh cosine similarity
	        List<productImage> allImages = productImageRepo.getListImageProduct();
	        List<ScoreProduct> scoredProducts = new ArrayList<>();

	        for (productImage image : allImages) {
	            try {
	                List<Double> productEmbedding = objectMapper.readValue(
	                    image.getImageEmbedding(), new TypeReference<List<Double>>() {});
	                double similarity = cosineSimilarity(queryEmbedding, productEmbedding);
	                scoredProducts.add(new ScoreProduct(image.getProduct(), similarity));
	            } catch (JsonProcessingException e) {
	                e.printStackTrace();
	            }
	        }

	        List<Product> topProducts = scoredProducts.stream()
	        		  .filter(scoreProduct -> scoreProduct.getScore() >= 0.7) 
	        	    .sorted(Comparator.comparingDouble(ScoreProduct::getScore).reversed())
	        	    .map(ScoreProduct::getProduct)
	        	    .collect(Collectors.toMap(
	        	        Product::getId_product, // dùng id làm key
	        	        p -> p,
	        	        (existing, replacement) -> existing, // nếu trùng thì giữ cái đầu tiên
	        	        LinkedHashMap::new
	        	    ))
	        	    .values().stream()
	        	    .limit(5)
	        	    .collect(Collectors.toList());


	        return convertToDisplayResponse(topProducts);
	}
	  private List<Double> getImageEmbedding(MultipartFile file) throws IOException {
	        String fastapiUrl = "http://localhost:8000/image-embedding-from-file/";

	        HttpHeaders headers = new HttpHeaders();
	        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

	        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
	        body.add("file", new MultipartInputStreamFileResource(file.getInputStream(), file.getOriginalFilename()));

	        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);
	        ResponseEntity<Map> response = restTemplate.postForEntity(fastapiUrl, requestEntity, Map.class);

	        if (response.getBody() == null || !response.getBody().containsKey("embedding")) {
	            throw new RuntimeException("Không thể lấy embedding từ FastAPI");
	        }

	        return (List<Double>) response.getBody().get("embedding");
	    }

	  @Override
	public List<DisplayProductResponse> convertToDisplayResponse(List<Product> products) {
	        return products.stream().map(product -> {
	            int id = product.getId_product();
	            String name = product.getNameProduct();
	            double price = product.getPrice();
	            double rate = product.getRate();

	            String ownerImage = product.getProductImages().stream()
	                .filter(img ->  img.getIsDelete() == 0)
	                .map(productImage::getImageUrl)
	                .findFirst()
	                .orElse(null);

	            List<String> images = product.getProductImages().stream()
	                .filter(img -> img.getIsDelete() == 0)
	                .map(productImage::getImageUrl)
	                .collect(Collectors.toList());

	            return new DisplayProductResponse(id, name, price, rate, ownerImage, images);
	        }).collect(Collectors.toList());
	    }
	@Override
	public Product getProductById(int id) {
	   Product product =productRepo.getById(id);
		return product;
	}
   @Override
   public DisplayProductResponse convertProductDisplay(Product product) {
         DisplayProductResponse display=new DisplayProductResponse();
         display.setId_product(product.getId_product());
         display.setNameProduct(product.getNameProduct());
         display.setPrice(product.getPrice());
         display.setOwnerImage(product.getProductImages().stream().filter(img->img.getIsDelete()==0) .map(productImage::getImageUrl)
	                .findFirst()
	                .orElse(null));
         List<String> images = product.getProductImages().stream()
	                .filter(img -> img.getIsDelete() == 0)
	                .map(productImage::getImageUrl)
	                .collect(Collectors.toList());

         display.setImages(images);
         return display;
   }
	
	  
}
