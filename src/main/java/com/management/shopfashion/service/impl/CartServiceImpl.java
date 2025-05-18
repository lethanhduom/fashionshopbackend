package com.management.shopfashion.service.impl;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.management.shopfashion.dto.CartDto;
import com.management.shopfashion.dto.CartItemDto;
import com.management.shopfashion.dto.ProductDetailDto;
import com.management.shopfashion.dto.response.CartItemResponse;
import com.management.shopfashion.entity.Cart;
import com.management.shopfashion.entity.CartItem;
import com.management.shopfashion.entity.ProductDetail;
import com.management.shopfashion.mapper.CartItemResponseMapper;
import com.management.shopfashion.mapper.CartMapper;
import com.management.shopfashion.repository.CartItemRepo;
import com.management.shopfashion.repository.CartRepo;
import com.management.shopfashion.repository.ProductDetailRepo;
import com.management.shopfashion.service.CartService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CartServiceImpl implements CartService {
	private CartRepo cartRepo;
	private CartItemRepo cartItemRepo;
	private ProductDetailRepo productDetailRepo;
	@Override
	public Cart createCart(CartDto cartDto) {
		Cart cart=cartRepo.save(CartMapper.MapCart(cartDto));
		return cart;
	}
	@Override
	public CartItem addItemToCart(CartItemDto cartItemDto) {
		
		 Boolean temp=false;
		 Cart cart=cartRepo.findById_cart(cartItemDto.getCart_id());
		 ProductDetail productDetail=productDetailRepo.getProductDetailById(cartItemDto.getId_product_detail());
		 CartItem cartItem=new CartItem();
		 cartItem.setCart(cart);
		 cartItem.setProductDetail(productDetail);
		 cartItem.setQuantity(cartItemDto.getQuantity());
		 List<CartItem>getListItem=cartItemRepo.getListCartItem(cartItemDto.getCart_id());
		 if(getListItem.size()==0) {
			 return cartItemRepo.save(cartItem);
		 }else {
			 for (CartItem item:getListItem) {
				 if(item.getProductDetail().getId_product_detail()==cartItemDto.getId_product_detail()) {
					item.setQuantity(cartItemDto.getQuantity()+item.getQuantity());
					temp=true;
					return cartItemRepo.save(item);
				
				 }
			 }
		 }
		 if(temp==false) {
			 return cartItemRepo.save(cartItem);
		 }
		return null;
		 
		
	}
	@Override
	public List<CartItemResponse> displayCartItem(int id_cart) {
		List<CartItem>getListCartItem=cartItemRepo.getListCartItem(id_cart);
		return getListCartItem.stream().map(cartitem->CartItemResponseMapper.MapCartItemResponse(cartitem)).collect(Collectors.toList());
	}
	@Override
	public void deleteCartItem(int id) {
		
		cartItemRepo.deleteById(id);
	}

}
