package com.management.shopfashion.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.management.shopfashion.dto.RevenueDTO;
import com.management.shopfashion.dto.request.OrderRequest;
import com.management.shopfashion.dto.response.OrderDetailResponse;
import com.management.shopfashion.dto.response.OrderResponse;
import com.management.shopfashion.entity.Order;

@Service
public interface OrderService {
	public Order createOrder(OrderRequest orderRequest);
	public Page<OrderResponse> getPageOrder (int page, int size);
	public void safelyDeleteCartItem(int cartItemId);
	public OrderDetailResponse getDetailOrder(int id);
	public void updateStatusOrder(int id, int status);
	public List<OrderDetailResponse> getAllOrderById(int id_user);
	public void handleOrderReturn(int id,int status,String reason,String description);
	List<RevenueDTO> getMonthlyRevenue(int year);
	List<RevenueDTO> getDailyRevenue(String month);

}
