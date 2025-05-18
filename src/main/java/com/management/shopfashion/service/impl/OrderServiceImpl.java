package com.management.shopfashion.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;

import com.management.shopfashion.dto.RevenueDTO;
import com.management.shopfashion.dto.request.OrderDetailRequest;
import com.management.shopfashion.dto.request.OrderRequest;
import com.management.shopfashion.dto.response.OrderDetailResponse;
import com.management.shopfashion.dto.response.OrderResponse;
import com.management.shopfashion.dto.response.OrderVariantResponse;
import com.management.shopfashion.entity.CartItem;
import com.management.shopfashion.entity.Order;
import com.management.shopfashion.entity.OrderDetail;
import com.management.shopfashion.entity.ProductDetail;
import com.management.shopfashion.mapper.OrderMapper;
import com.management.shopfashion.repository.CartItemRepo;
import com.management.shopfashion.repository.CouponRepo;
import com.management.shopfashion.repository.OrderDetailRepo;
import com.management.shopfashion.repository.OrderRepo;
import com.management.shopfashion.repository.ProductDetailRepo;
import com.management.shopfashion.repository.UserRepo;
import com.management.shopfashion.service.CartService;
import com.management.shopfashion.service.OrderService;
import com.management.shopfashion.service.ProductService;
import com.management.shopfashion.service.TimeService;

import lombok.AllArgsConstructor;
@Service
@AllArgsConstructor
public class OrderServiceImpl implements OrderService {
    
	private OrderRepo orderRepo;
	private OrderDetailRepo orderRetailRepo;
	private UserRepo userRepo;
	private CouponRepo couponRepo;
	private ProductDetailRepo productDetailRepo;
	private CartService cartService;
	private ProductService productService;
	private TimeService timeService;
	private CartItemRepo cartItemRepo;
	@Override
	public Order createOrder(OrderRequest orderRequest)
	{
	Order order=new Order();
	order.setTotalPrice(orderRequest.getTotalPrice());
	
	order.setStatus(orderRequest.getStatus());
	order.setUser(userRepo.getUser(orderRequest.getUsername()));
	order.setPaymentMethod(orderRequest.getPaymentMethod());
	order.setAddress(orderRequest.getAddress());
	order.setPhoneNumber(orderRequest.getPhoneNumber());
	order.setShipFee(orderRequest.getShipFee());
	order.setShipFeeCoupon(order.getShipFeeCoupon());
	order.setTotalPriceProduct(orderRequest.getTotalPriceProduct());
	order.setTotalPriceCoupon(orderRequest.getTotalPriceCoupon());
	order.setProductFeeCoupon(orderRequest.getProductFeeCoupon());
	order.setRecipientName(orderRequest.getRecipientName());
	order.setDateOrder(timeService.getCurrentVietnamTimeFormatted());
	if (orderRequest.getCouponProductId()!=null) {
		 order.setCouponProduct(couponRepo.findCouponByCode(orderRequest.getCouponProductId()));
	}
	if(orderRequest.getCouponShipppingId()!=null) {
		order.setCouponShipping(couponRepo.findCouponByCode(orderRequest.getCouponShipppingId()));
		
	}
	Order saveOrder=orderRepo.save(order);
	List<OrderDetail>listOrderDetail=new ArrayList<>();
	for(OrderDetailRequest detailRequest:orderRequest.getOrderDetails()) {
		OrderDetail orderDetail=new OrderDetail();
		orderDetail.setPrice(detailRequest.getPrice());
		orderDetail.setQuantity(detailRequest.getQuantity());
		orderDetail.setOrder_invoice(saveOrder);
		orderDetail.setProductDetail(productDetailRepo.getProductDetailById(detailRequest.getId_product_detail()));
		safelyDeleteCartItem(detailRequest.getId_cart_item());
		productService.ChangeQuantityProductDetail(detailRequest.getId_product_detail(), detailRequest.getQuantity());
		
		listOrderDetail.add(orderDetail);
	}
    orderRetailRepo.saveAll(listOrderDetail);
	saveOrder.setOrderdetails(listOrderDetail);
	return saveOrder;
	}
	@Override
	public Page<OrderResponse> getPageOrder(int page, int size) {
		Page<Order>getPageOrder=orderRepo.getPageOrder(PageRequest.of(page, size));
		return getPageOrder.map(order->OrderMapper.MapOrderResponse(order));
	}
	@Override
	public void safelyDeleteCartItem(int cartItemId) {
		 try {
		        Optional<CartItem> optional =cartItemRepo.findById(cartItemId);
		        optional.ifPresent(cartItemRepo::delete);
		    } catch (ObjectOptimisticLockingFailureException | EmptyResultDataAccessException ex) {
		        System.out.println(" CartItem không tồn tại hoặc đã bị thay đổi: ID = " + cartItemId);
		        
		    }
		
	}
	@Override
	public OrderDetailResponse getDetailOrder(int id) {
		Order order=orderRepo.getById(id);
		OrderDetailResponse orderDetail=new OrderDetailResponse();
		orderDetail.setId_order(order.getId_order());
		orderDetail.setTotalPrice(order.getTotalPrice());
		orderDetail.setUsername(order.getUser().getUsername());
		orderDetail.setCouponProduct(order.getCouponProduct()!=null?order.getCouponProduct().getCode():null);
		orderDetail.setCouponShippping(order.getCouponShipping()!=null?order.getCouponShipping().getCode():null);
		orderDetail.setPaymentMethod(order.getPaymentMethod());
		orderDetail.setShipFee(order.getShipFee());
		orderDetail.setTotalPriceCoupon(order.getTotalPriceCoupon());
		orderDetail.setShipFeeCoupon(order.getShipFeeCoupon());
		orderDetail.setTotalPriceProduct(order.getTotalPriceProduct());
		orderDetail.setAddress(order.getAddress());
		orderDetail.setRecipientName(order.getRecipientName());
		orderDetail.setStatus(order.getStatus());
		orderDetail.setPhoneNumber(order.getPhoneNumber());
		orderDetail.setReason(order.getReason()==null?null:order.getReason());
		orderDetail.setDescriptionReason(order.getDescriptionReason()==null?null:order.getDescriptionReason());
		orderDetail.setDetailOrders(order.getOrderdetails().stream().map(order_detail->OrderMapper.MapOrderVariantResponse(order_detail)).collect(Collectors.toList()));
		return orderDetail;
	}
	@Override
	public void updateStatusOrder(int id, int status) {
		Order order=orderRepo.getById(id);
		order.setStatus(status);
		if(status==4) {
			order.setDateFinal(timeService.getCurrentVietnamTimeFormatted());
		}
		orderRepo.save(order);
	}
	@Override
	public List<OrderDetailResponse> getAllOrderById(int id_user) {
		List<Order>getListOrder=orderRepo.getAllOrderByIdUser(id_user);
		
	return getListOrder.stream().map(order->OrderMapper.MapOrderDetailResponse(order)).collect(Collectors.toList());
		
	}
	@Override
	public void handleOrderReturn(int id, int status, String reason,String description) {
			Order order=orderRepo.getById(id);
			order.setReason(reason);
			order.setStatus(status);
			order.setDescriptionReason(description);
			for(OrderDetail orderDetail:order.getOrderdetails()) {
				productService.ChangeQuantityProductDetail(orderDetail.getProductDetail().getId_product_detail(), -orderDetail.getQuantity());
			}
			orderRepo.save(order);
		
	}
	@Override
	public List<RevenueDTO> getMonthlyRevenue(int year) {
		 List<Object[]> results = orderRepo.getMonthlyRevenueByYear(year);
	        return results.stream().map(obj ->
	            new RevenueDTO("Tháng " + obj[0], ((Number) obj[1]).doubleValue())
	        ).collect(Collectors.toList());
	}
	@Override
	public List<RevenueDTO> getDailyRevenue(String month) {
		 List<Object[]> results = orderRepo.getDailyRevenueByMonth(month);
	        return results.stream().map(obj ->
	            new RevenueDTO((String) obj[0], ((Number) obj[1]).doubleValue())
	        ).collect(Collectors.toList());
	}

	
}
