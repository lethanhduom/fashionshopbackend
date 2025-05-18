package com.management.shopfashion.dto.response;

import java.util.List;

import com.management.shopfashion.dto.request.OrderDetailRequest;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetailResponse {
	private int id_order;
	private double totalPrice;
    private String username;
    private String couponShippping;
    private String couponProduct;
    private String paymentMethod;
	private double shipFee;
	private double totalPriceCoupon;
	private double shipFeeCoupon;
	private double productFeeCoupon;
	private double totalPriceProduct;
	private String address;
	private String recipientName;
	private String phoneNumber;
	private int status;
	private String reason;
	private String descriptionReason;
	List<OrderVariantResponse> DetailOrders;
    
}
