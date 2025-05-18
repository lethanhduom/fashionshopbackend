package com.management.shopfashion.dto.response;

import com.management.shopfashion.dto.CouponDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CouponeResponse {
CouponDto couponDto;
String error;
}
