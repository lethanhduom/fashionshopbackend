package com.management.shopfashion.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.management.shopfashion.dto.CategoryDto;
import com.management.shopfashion.dto.request.CategoryResquest;
import com.management.shopfashion.entity.Category;

@Service
public interface CategoryService {
	public CategoryDto createCategory(CategoryResquest categoryRequest);
	public CategoryDto updateCategory(CategoryDto categoryDto,int id);
	public List<CategoryDto>getListCategory(int isDelete);
	public Page<CategoryDto> getPagecategory(int page,int size, String query);
	public void actionCategory(int id);
	public CategoryDto eidtCategoryAdmin(CategoryResquest categoryRequest, int id);
	public CategoryDto getCategory(int id);
}
