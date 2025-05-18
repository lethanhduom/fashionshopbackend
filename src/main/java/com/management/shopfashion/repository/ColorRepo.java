package com.management.shopfashion.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.management.shopfashion.dto.ColorDto;
import com.management.shopfashion.entity.Color;

public interface ColorRepo extends JpaRepository<Color, Integer> {
	@Query("select c from Color c where id_color=?1")
	public Color findByIdColor(int id);
	@Query("select c from Color c")
	Page<Color>getlistColor(Pageable pageable);
}
