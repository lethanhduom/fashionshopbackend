package com.management.shopfashion.mapper;

import com.management.shopfashion.dto.CategoryDto;
import com.management.shopfashion.entity.Category;

public class CategoryMapper {
	public static CategoryDto MapCategoryDto(Category category) {
		if(category==null)
			return null;
		CategoryDto categoryDto=new CategoryDto();
		categoryDto.setId_category(category.getId_category());
		categoryDto.setNameCategory(category.getNameCategory());
		categoryDto.setCreateTime(category.getCreateTime());
		categoryDto.setIsDelete(category.getIsDelete());
		categoryDto.setProductFor(category.getProductFor());
		categoryDto.setImageUrl(category.getImageUrl());
		return categoryDto;
	}
	public static Category MapCategory(CategoryDto categoryDto) {
		if(categoryDto==null)
			return null;
		Category category=new Category();
		category.setId_category(categoryDto.getId_category());
		category.setNameCategory(categoryDto.getNameCategory());
		category.setCreateTime(categoryDto.getCreateTime());
		category.setIsDelete(categoryDto.getIsDelete());
		category.setProductFor(categoryDto.getProductFor());
		category.setImageUrl(categoryDto.getImageUrl());
		return category;
	}
}
