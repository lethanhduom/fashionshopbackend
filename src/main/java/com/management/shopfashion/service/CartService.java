package com.management.shopfashion.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.management.shopfashion.dto.CartDto;
import com.management.shopfashion.dto.CartItemDto;
import com.management.shopfashion.dto.response.CartItemResponse;
import com.management.shopfashion.entity.Cart;
import com.management.shopfashion.entity.CartItem;

@Service
public interface CartService {
	public Cart createCart(CartDto cartDto);
	public CartItem addItemToCart(CartItemDto cartItemDto);
	public List<CartItemResponse> displayCartItem(int id_cart);
	public void deleteCartItem(int id);
		
	
}
