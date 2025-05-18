package com.management.shopfashion.service.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.management.shopfashion.dto.CouponDto;
import com.management.shopfashion.dto.response.CouponeResponse;
import com.management.shopfashion.entity.Coupon;
import com.management.shopfashion.exception.UserAlreadyExistsException;
import com.management.shopfashion.mapper.CouponMapper;
import com.management.shopfashion.repository.CouponRepo;
import com.management.shopfashion.service.CouponService;

import lombok.AllArgsConstructor;
@Service
@AllArgsConstructor
public class CouponServiceImpl implements CouponService {
	private CouponRepo couponRepo;
	@Override
	public CouponeResponse createCoupon(CouponDto couponDto) {
		CouponeResponse couponeResponse=new CouponeResponse();
		if(couponRepo.findCouponByCode(couponDto.getCode())!=null) {
			couponeResponse.setCouponDto(null);
			couponeResponse.setError("Code Name exits");
			 return couponeResponse;
		}else {
			couponDto.setIsDelete(0);
			Coupon coupon=couponRepo.save(CouponMapper.MapCoupon(couponDto));
			couponeResponse.setCouponDto(CouponMapper.MapCouponDto(coupon));
			couponeResponse.setError(null);
			deactivateExpiredCoupons();
			return couponeResponse;
		}
		
	}
	@Override
	@Scheduled(cron = "0 0 0 * * *")
	public void deactivateExpiredCoupons() {
		List<Coupon> coupons = couponRepo.findCouponActive(0);
        LocalDate today = LocalDate.now();
        for (Coupon coupon : coupons) {
            if (coupon.getEndDate() != null &&
                coupon.getEndDate().isBefore(today) &&
                coupon.getIsDelete() == 0) {

                coupon.setIsDelete(1);
                couponRepo.save(coupon);
                System.out.println("Coupon hết hạn đã được cập nhật: " + coupon.getCode());
            }
        }
	}
	@Override
	public Page<CouponDto> getCouponPage(int isDelete, int page, int size) {
		Page<Coupon>getPageCoupon=couponRepo.getPageCoupon(isDelete, PageRequest.of(page, size));
		return getPageCoupon.map(coupon->CouponMapper.MapCouponDto(coupon));
	}
	@Override
	public CouponDto getCouponById(int id) {
		Coupon getCoupon=couponRepo.getById(id);
		return CouponMapper.MapCouponDto(getCoupon);
	}
	@Override
	public CouponDto updateCoupon(CouponDto couponDto,int id) {
		Coupon findCoupon=couponRepo.getCouponbyId(id);
		findCoupon.setCategory(couponDto.getCategory());
		findCoupon.setCode(couponDto.getCode());
		findCoupon.setTypeCode(couponDto.getTypeCode());
		findCoupon.setDecreaseCrash(couponDto.getDecreaseCrash());
		findCoupon.setDecreasePercent(couponDto.getDecreasePercent());
		findCoupon.setMinOrderValue(couponDto.getMinOrderValue());
		findCoupon.setStartDate(couponDto.getStartDate());
		findCoupon.setEndDate(couponDto.getEndDate());
	    findCoupon.setIsDelete(couponDto.getIsDelete());
	    couponRepo.save(findCoupon);
		return null;
	}
	@Override
	public List<CouponDto> getListCouponActive() {
		List<Coupon>getListCoupon=couponRepo.findCouponActive(0);
		return getListCoupon.stream().map(coupon->CouponMapper.MapCouponDto(coupon)).collect(Collectors.toList());
	}


}
