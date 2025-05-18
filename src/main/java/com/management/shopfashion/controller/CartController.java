package com.management.shopfashion.controller;

import java.util.List;

import org.apache.http.protocol.HTTP;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.management.shopfashion.dto.CartItemDto;
import com.management.shopfashion.dto.response.CartItemResponse;
import com.management.shopfashion.entity.CartItem;
import com.management.shopfashion.service.CartService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("api/cart")
public class CartController {
	private final CartService cartService;
@PostMapping("/add-item")
public ResponseEntity<CartItem>addCartItem(@RequestBody CartItemDto cartItemDto){
	CartItem cartItem=cartService.addItemToCart(cartItemDto);
	return ResponseEntity.ok(cartItem);
}
@GetMapping("/list")
public ResponseEntity<List<CartItemResponse>> getList(@RequestParam int id){
	return ResponseEntity.ok(cartService.displayCartItem(id));
}
@DeleteMapping("/delete/{id}")
public ResponseEntity<?>DeleteCartItem(@PathVariable int id){
	cartService.deleteCartItem(id);
	return ResponseEntity.ok("delete successful");
}
}
