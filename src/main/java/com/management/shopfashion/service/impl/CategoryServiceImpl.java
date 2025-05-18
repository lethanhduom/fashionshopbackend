package com.management.shopfashion.service.impl;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.management.shopfashion.dto.CategoryDto;
import com.management.shopfashion.dto.request.CategoryResquest;
import com.management.shopfashion.entity.Category;
import com.management.shopfashion.entity.Product;
import com.management.shopfashion.mapper.CategoryMapper;
import com.management.shopfashion.repository.CategoryRepo;
import com.management.shopfashion.repository.ProductRepo;
import com.management.shopfashion.repository.UserRepo;
import com.management.shopfashion.service.CategoryService;
import com.management.shopfashion.service.CloudinaryService;
import com.management.shopfashion.service.TimeService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CategoryServiceImpl implements CategoryService {
	 private CategoryRepo categoryRepo;
	 private CloudinaryService cloudinaryService;
	 private TimeService timeService;
	 private ProductRepo productRepo;
	@Override
	public CategoryDto createCategory(CategoryResquest categoryRequest) {
	
		String url=null;
		if(categoryRequest.getImage()!=null) {
		 try {
			url=cloudinaryService.uploadImage(categoryRequest.getImage());
		} catch (IOException e) {
		
			e.printStackTrace();
		}
		}
		CategoryDto categoryDto=new CategoryDto();
		categoryDto.setCreateTime(timeService.getCurrentVietnamTimeFormatted());
		categoryDto.setNameCategory(categoryRequest.getNameCategory());
		categoryDto.setIsDelete(0);
		categoryDto.setProductFor(categoryRequest.getProductFor());
		categoryDto.setImageUrl(url);
		Category category=CategoryMapper.MapCategory(categoryDto);
	    Category saveCategory=categoryRepo.save(category);
		return CategoryMapper.MapCategoryDto(saveCategory);
	
	}
	@Override
	public CategoryDto updateCategory(CategoryDto categoryDto, int id) {
		Category category=categoryRepo.findById_category(id);
		category.setIsDelete(categoryDto.getIsDelete());
		category.setNameCategory(categoryDto.getNameCategory());
		
		Category saveCate=categoryRepo.save(category);
		return CategoryMapper.MapCategoryDto(category);
	}
	@Override
	public List<CategoryDto> getListCategory(int isDelete) {

		return categoryRepo.getListCategory(isDelete).stream().map(category->CategoryMapper.MapCategoryDto(category)).collect(Collectors.toList());
	}
	@Override
	public Page<CategoryDto> getPagecategory(int page, int size, String query) {
		Page<Category>getPage=null;
		if(query==null||query.trim()=="") {
			getPage=categoryRepo.getPageCategory(PageRequest.of(page, size));
		}else {
			getPage=categoryRepo.searchCategoriesByKeyword(query, PageRequest.of(page, size));
		}
		
		return getPage.map(category->CategoryMapper.MapCategoryDto(category));
	}
	@Override
	public void actionCategory(int id) {
		Category category=categoryRepo.findById_category(id);
		int is_delete=category.getIsDelete()==0?1:0;
		category.setIsDelete(is_delete);
	
			 for(Product product: category.getProducts()) {
				    product.setIsDelete(is_delete);
				    productRepo.save(product);
		     }
		
	    
		categoryRepo.save(category);
		
		
	}
	@Override
	public CategoryDto eidtCategoryAdmin(CategoryResquest categoryRequest, int id) {
	   Category category=categoryRepo.findById_category(id);
	   category.setNameCategory(categoryRequest.getNameCategory());
	   category.setProductFor(categoryRequest.getProductFor());
	   
	   if(categoryRequest.getImage()!=null) {
		   try {
			String	url=cloudinaryService.uploadImage(categoryRequest.getImage());
			category.setImageUrl(url);
			} catch (IOException e) {
			
				e.printStackTrace();
	   }
		  
		
	}
	   categoryRepo.save(category);
	   return CategoryMapper.MapCategoryDto(category);
	}
	@Override
	public CategoryDto getCategory(int id) {
		Category category=categoryRepo.findById_category(id);
		return CategoryMapper.MapCategoryDto(category);
	}
	

}
