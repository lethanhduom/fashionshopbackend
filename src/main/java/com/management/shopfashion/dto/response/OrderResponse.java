package com.management.shopfashion.dto.response;

import java.util.List;

import com.management.shopfashion.dto.request.OrderDetailRequest;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderResponse {
	private int id_order;
	private double totalPrice;
    private int status;
    private String username;
    private int id_user;
    private String couponShipppingId;
    private String couponProductId;
    private String paymentMethod;;
	private double totalPriceCoupon;
	private String address;
	private String orderDate;
	private String recipient_name;

}
