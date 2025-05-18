package com.management.shopfashion.mapper;

import com.management.shopfashion.dto.CouponDto;
import com.management.shopfashion.entity.Coupon;

public class CouponMapper {
	public static CouponDto MapCouponDto(Coupon coupon) {
		if (coupon==null)
			return null;
		CouponDto couponDto=new CouponDto();
		couponDto.setId_coupon(coupon.getId_coupon());
		couponDto.setCode(coupon.getCode());
		couponDto.setTypeCode(coupon.getTypeCode());
		couponDto.setDecreaseCrash(coupon.getDecreaseCrash());
		couponDto.setMinOrderValue(coupon.getMinOrderValue());
		couponDto.setStartDate(coupon.getStartDate());
		couponDto.setEndDate(coupon.getEndDate());
		couponDto.setCategory(coupon.getCategory());
		couponDto.setIsDelete(coupon.getIsDelete());
		couponDto.setDecreasePercent(coupon.getDecreasePercent());
		return couponDto;
	}
	public static Coupon MapCoupon(CouponDto couponDto) {
		if (couponDto==null)
			return null;
		Coupon coupon=new Coupon();
		coupon.setId_coupon(couponDto.getId_coupon());
		coupon.setCode(couponDto.getCode());
		coupon.setTypeCode(couponDto.getTypeCode());
		coupon.setDecreaseCrash(couponDto.getDecreaseCrash());
		coupon.setMinOrderValue(couponDto.getMinOrderValue());
		coupon.setStartDate(couponDto.getStartDate());
		coupon.setEndDate(couponDto.getEndDate());
		coupon.setCategory(couponDto.getCategory());
		coupon.setIsDelete(couponDto.getIsDelete());
		coupon.setDecreasePercent(couponDto.getDecreasePercent());
		return coupon;
	}
}

