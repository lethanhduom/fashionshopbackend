package com.management.shopfashion.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.management.shopfashion.entity.Cart;
import java.util.List;


public interface CartRepo extends JpaRepository<Cart, Integer> {
	@Query("Select c From Cart c where id_cart=?1")
	public Cart  findById_cart(int id_cart);
}
