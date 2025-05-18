package com.management.shopfashion.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.management.shopfashion.entity.Coupon;

public interface CouponRepo extends JpaRepository<Coupon, Integer> {
	@Query("select c from Coupon c where isDelete=?1")
	Page<Coupon>getPageCoupon(int isDelete, Pageable pageable); 
	@Query("select c from Coupon c where id_coupon=?1")
	Coupon getCouponbyId(int id);
	@Query ("select c from Coupon c where code=?1")
	Coupon findCouponByCode (String code);
	@Query ("select c from Coupon c where isDelete=?1")
	List<Coupon> findCouponActive(int isDelete);


}
