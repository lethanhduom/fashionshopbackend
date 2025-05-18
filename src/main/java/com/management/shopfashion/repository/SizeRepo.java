package com.management.shopfashion.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.management.shopfashion.entity.Size;

public interface SizeRepo extends JpaRepository<Size, Integer> {
	@Query("Select s from Size s where id_size=?1")
	public Size findSizeById(int id);
	@Query("Select s from Size s")
	public Page<Size>getPageSize(Pageable pageable);
}
