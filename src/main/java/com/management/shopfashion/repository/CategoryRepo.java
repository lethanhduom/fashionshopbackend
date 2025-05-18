package com.management.shopfashion.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.management.shopfashion.entity.Category;

import java.util.List;


public interface CategoryRepo extends JpaRepository<Category,Integer> {
	@Query("select c from Category c where id_category=?1")
	public Category findById_category(int id);
	@Query("select c from Category c")
	public Page<Category>getPageCategory(Pageable pageable);
	@Query ("select c from Category c where isDelete=?1")
	public List<Category>getListCategory(int isDelete);
	  @Query("SELECT c FROM Category c WHERE (LOWER(c.nameCategory) LIKE LOWER(CONCAT('%', ?1, '%')) OR LOWER(c.productFor) LIKE LOWER(CONCAT('%', ?1, '%')))")
	    Page<Category> searchCategoriesByKeyword(String keyword, Pageable pageable);
}
