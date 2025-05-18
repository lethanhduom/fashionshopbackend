package com.management.shopfashion.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.management.shopfashion.dto.CouponDto;
import com.management.shopfashion.dto.response.CouponeResponse;
import com.management.shopfashion.entity.Coupon;

@Service
public interface CouponService {
public CouponeResponse createCoupon(CouponDto couponDto);
public void deactivateExpiredCoupons();
public Page<CouponDto> getCouponPage(int isDelete,int page, int size);
public CouponDto getCouponById(int id);
public CouponDto updateCoupon(CouponDto couponDto, int id);
public List<CouponDto> getListCouponActive();
}
