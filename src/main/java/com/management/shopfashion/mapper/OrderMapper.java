package com.management.shopfashion.mapper;

import java.util.List;
import java.util.stream.Collectors;

import com.management.shopfashion.dto.response.OrderDetailResponse;
import com.management.shopfashion.dto.response.OrderResponse;
import com.management.shopfashion.dto.response.OrderVariantResponse;
import com.management.shopfashion.entity.Order;
import com.management.shopfashion.entity.OrderDetail;

public class OrderMapper {
public static OrderResponse MapOrderResponse(Order order) {
	if(order==null)
		return null;
	OrderResponse orderResponse=new OrderResponse();
	orderResponse.setId_order(order.getId_order());
	orderResponse.setTotalPrice(order.getTotalPrice());
	orderResponse.setStatus(order.getStatus());
	orderResponse.setUsername(order.getUser().getUsername());
	orderResponse.setId_user(order.getUser().getId_user());
    orderResponse.setPaymentMethod(order.getPaymentMethod());
    orderResponse.setTotalPriceCoupon(order.getTotalPriceCoupon());
    orderResponse.setAddress(order.getAddress());
    orderResponse.setOrderDate(order.getDateOrder());
    orderResponse.setRecipient_name(order.getRecipientName());
   
    return orderResponse;
}
public static OrderVariantResponse MapOrderVariantResponse(OrderDetail order_detail) {
	if(order_detail==null)
		return null;
	return new OrderVariantResponse(order_detail.getId_order_detail(),
			order_detail.getPrice(),
			order_detail.getProductDetail().getProductImage().getImageUrl(),
			order_detail.getQuantity(),
			order_detail.getProductDetail().getProduct().getNameProduct(),
			order_detail.getProductDetail().getColor().getCodeColor(),
			order_detail.getProductDetail().getSize().getNameSize(),
			order_detail.getProductDetail().getId_product_detail()
			);
}
public static OrderDetailResponse MapOrderDetailResponse(Order order) {
	OrderDetailResponse orderDetailResponse =new OrderDetailResponse();
	orderDetailResponse.setTotalPrice(order.getTotalPrice());
	orderDetailResponse.setCouponProduct(order.getCouponProduct()!=null? order.getCouponProduct().getCode():null);
	orderDetailResponse.setCouponShippping(order.getCouponShipping()!=null?order.getCouponShipping().getCode():null);
	orderDetailResponse.setPaymentMethod(order.getPaymentMethod());
	orderDetailResponse.setShipFee(order.getShipFee());
	orderDetailResponse.setTotalPriceCoupon(order.getTotalPriceCoupon());
	orderDetailResponse.setShipFee(order.getShipFeeCoupon());
	orderDetailResponse.setProductFeeCoupon(order.getProductFeeCoupon());
	orderDetailResponse.setTotalPriceProduct(order.getTotalPriceProduct());
	orderDetailResponse.setAddress(order.getAddress());
	orderDetailResponse.setRecipientName(order.getRecipientName());
	orderDetailResponse.setPhoneNumber(order.getPhoneNumber());
	orderDetailResponse.setStatus(order.getStatus());
	orderDetailResponse.setId_order(order.getId_order());
	orderDetailResponse.setDetailOrders(order.getOrderdetails().stream().map(order_detail->MapOrderVariantResponse(order_detail)).collect(Collectors.toList()));
	 return orderDetailResponse;
}

}
