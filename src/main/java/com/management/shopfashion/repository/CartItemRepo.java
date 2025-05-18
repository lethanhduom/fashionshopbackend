package com.management.shopfashion.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.management.shopfashion.entity.CartItem;

public interface CartItemRepo extends JpaRepository<CartItem, Integer> {
  @Query("select c from CartItem c where c.cart.id_cart=?1")
 public  List<CartItem>getListCartItem(int id);
 
}
