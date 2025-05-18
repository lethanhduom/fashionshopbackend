package com.management.shopfashion.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.management.shopfashion.dto.CouponDto;
import com.management.shopfashion.dto.response.CouponeResponse;
import com.management.shopfashion.entity.Coupon;
import com.management.shopfashion.service.CouponService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/api/coupon")
public class CouponController {
	private CouponService couponService;
	@PostMapping("/create")
public ResponseEntity<CouponeResponse>createCoupon(@RequestBody CouponDto couponDto){
	return ResponseEntity.ok(couponService.createCoupon(couponDto));
	}
	@GetMapping("/get-page")
	public ResponseEntity<Page<CouponDto>>getPageCoupon(
			@RequestParam int isDelete,
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size
			){
		return ResponseEntity.ok(couponService.getCouponPage(isDelete, page, size));
	}
	@GetMapping("/get-detail")
	public ResponseEntity<CouponDto>getCoupon(@RequestParam int id){
		return ResponseEntity.ok(couponService.getCouponById(id));
	}
	@PutMapping("/update-coupon")
	public ResponseEntity<CouponDto>updateCoupon(@RequestBody CouponDto couponDto){
		return ResponseEntity.ok(couponService.updateCoupon(couponDto, couponDto.getId_coupon()));
	}
	@GetMapping("/coupon-active")
	public ResponseEntity<List<CouponDto>>getListCouponCategory(){
		return ResponseEntity.ok(couponService.getListCouponActive());
	}
}
