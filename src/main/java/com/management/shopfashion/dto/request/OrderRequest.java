package com.management.shopfashion.dto.request;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequest {
	private double totalPrice;
    private int status;
    private String username;
    private String couponShipppingId;
    private String couponProductId;
    private String paymentMethod;
	private double shipFee;
	private double totalPriceCoupon;
	private double shipFeeCoupon;
	private double productFeeCoupon;
	private double totalPriceProduct;
	private String address;
	private String recipientName;
	private String phoneNumber;
    private List<OrderDetailRequest> orderDetails;
    

}
