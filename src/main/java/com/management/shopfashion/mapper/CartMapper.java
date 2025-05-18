package com.management.shopfashion.mapper;

import com.management.shopfashion.dto.CartDto;
import com.management.shopfashion.dto.CategoryDto;
import com.management.shopfashion.entity.Cart;
import com.management.shopfashion.entity.Category;

public class CartMapper {
	public static CartDto MapCartDto(Cart cart) {
		CartDto cartDto=new CartDto();
		cartDto.setId_cart(cart.getId_cart());
		return cartDto;
	}
	public static Cart MapCart(CartDto cartDto) {
		Cart cart=new Cart();
		cart.setId_cart(cartDto.getId_cart());
		return cart;

		}
}
