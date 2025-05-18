package com.management.shopfashion.mapper;

import com.management.shopfashion.dto.response.CartItemResponse;
import com.management.shopfashion.entity.CartItem;

public class CartItemResponseMapper {
public static CartItemResponse MapCartItemResponse(CartItem cartItem) {
	if(cartItem==null)
		return null;
	CartItemResponse response=new CartItemResponse();
	response.setId_cart(cartItem.getCart().getId_cart());
	response.setId_cart_item(cartItem.getId_cart_item());
	response.setId_product_detail(cartItem.getProductDetail().getId_product_detail());
	response.setQuantity(cartItem.getQuantity());
	response.setName(cartItem.getProductDetail().getProduct().getNameProduct());
	response.setColor(cartItem.getProductDetail().getColor().getCodeColor());
	response.setImage(cartItem.getProductDetail().getProductImage().getImageUrl());
	response.setPrice(cartItem.getProductDetail().getProduct().getPrice());
	response.setSize(cartItem.getProductDetail().getSize().getNameSize());
	return response;
}
}
