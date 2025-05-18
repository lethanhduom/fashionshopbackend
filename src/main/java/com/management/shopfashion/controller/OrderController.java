package com.management.shopfashion.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.management.shopfashion.config.VNPayConfig;
import com.management.shopfashion.dto.RevenueDTO;
import com.management.shopfashion.dto.request.OrderRequest;
import com.management.shopfashion.dto.response.OrderDetailResponse;
import com.management.shopfashion.dto.response.OrderResponse;
import com.management.shopfashion.dto.response.ShippingResponse;
import com.management.shopfashion.entity.Order;
import com.management.shopfashion.service.OrderService;
import com.management.shopfashion.service.ShippingService;
import com.management.shopfashion.service.VNPayService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/api/order")
public class OrderController {
	private OrderService orderService;
	private ShippingService shippingService;
	private VNPayService VNPayService;
 @PostMapping("/create-order")
 public ResponseEntity<Order>createOrder(@RequestBody OrderRequest orderRequest){
	 return ResponseEntity.ok(orderService.createOrder(orderRequest));
 }
 @GetMapping("/calculate")
 public Map<String, Object> getShippingFee(@RequestParam String address) {
     double distance = shippingService.getDistanceKm(address);
     double fee = shippingService.calculateFee(distance);

     Map<String, Object> result = new HashMap<>();
     result.put("address", address);
     result.put("distance_km", distance);
     result.put("shipping_fee", fee);

     return result;
 }
 
 @GetMapping("/revenue/monthly")
 public List<RevenueDTO> getMonthlyRevenue(@RequestParam int year) {
     return orderService.getMonthlyRevenue(year);
 }
 @GetMapping("/revenue/daily")
 public List<RevenueDTO> getDailyRevenue(@RequestParam String month) {
     return orderService.getDailyRevenue(month); // Ví dụ: "04-2025"
 }

	@PostMapping("/pay-vnpay")
	public Map<String, String> create(@RequestParam String amount) throws UnsupportedEncodingException{
		
	String vnp_Version = "2.1.0";
     String vnp_Command = "pay";
     String orderType = "other";
     
     String vnp_TxnRef = VNPayService.getRandomNumber(8);
     String vnp_IpAddr = "127.0.0.1";
     
     Map<String, String> response = new HashMap<>();
     
     
try {
	  Map<String, String> vnp_Params = new HashMap<>();
   vnp_Params.put("vnp_Version", vnp_Version);
   vnp_Params.put("vnp_Command", vnp_Command);
   vnp_Params.put("vnp_TmnCode", VNPayConfig.vnp_TmnCode);
   vnp_Params.put("vnp_Amount", String.valueOf(String.valueOf(Long.parseLong(amount)*100)));
   vnp_Params.put("vnp_CurrCode", "VND");
   
//   vnp_Params.put("vnp_BankCode", bankCode);
   vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
   vnp_Params.put("vnp_OrderInfo", "Thanh toan don hang:" + vnp_TxnRef);
   vnp_Params.put("vnp_OrderType", orderType);

   vnp_Params.put("vnp_Locale", "vn");
   vnp_Params.put("vnp_ReturnUrl", VNPayConfig.vnp_ReturnUrl);
   vnp_Params.put("vnp_IpAddr", vnp_IpAddr);

   Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
   SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
   String vnp_CreateDate = formatter.format(cld.getTime());
   vnp_Params.put("vnp_CreateDate", vnp_CreateDate);
   
   cld.add(Calendar.MINUTE, 15);
   String vnp_ExpireDate = formatter.format(cld.getTime());
   vnp_Params.put("vnp_ExpireDate", vnp_ExpireDate);
   
   List fieldNames = new ArrayList(vnp_Params.keySet());
   Collections.sort(fieldNames);
   StringBuilder hashData = new StringBuilder();
   StringBuilder query = new StringBuilder();
   Iterator itr = fieldNames.iterator();
   while (itr.hasNext()) {
       String fieldName = (String) itr.next();
       String fieldValue = (String) vnp_Params.get(fieldName);
       if ((fieldValue != null) && (fieldValue.length() > 0)) {
           //Build hash data
           hashData.append(fieldName);
           hashData.append('=');
           hashData.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
           //Build query
           query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII.toString()));
           query.append('=');
           query.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
           if (itr.hasNext()) {
               query.append('&');
               hashData.append('&');
           }
       }
   }
   String queryUrl = query.toString();
   String vnp_SecureHash = VNPayService.hmacSHA512(VNPayConfig.vnp_HashSecret, hashData.toString());
   queryUrl += "&vnp_SecureHash=" + vnp_SecureHash;
   String paymentUrl = VNPayConfig.vnp_Url + "?" + queryUrl;
   response.put("paymentUrl", paymentUrl);
}catch (Exception e) {
	e.printStackTrace();
}
 
     
		return response ;
	}
	
	@GetMapping("/admin-page")
	public ResponseEntity<Page<OrderResponse>>getPageOrder(
			@RequestParam (defaultValue = "0") int page,
			@RequestParam (defaultValue = "10") int size
			
			){
		return ResponseEntity.ok(orderService.getPageOrder(page, size));
	}
	
	@GetMapping("/order-detail")
	public ResponseEntity<OrderDetailResponse>getOrderDetaiResponse(@RequestParam int id){
		return ResponseEntity.ok(orderService.getDetailOrder(id));
	}
	@PutMapping ("/update-status")
	public ResponseEntity<?>updateOrder(@RequestParam int id, @RequestParam int status){
		orderService.updateStatusOrder(id, status);
		return ResponseEntity.ok().build();
	}
	@GetMapping("/get-order")
	public ResponseEntity<List<OrderDetailResponse>>getOrder(@RequestParam int id){
		return ResponseEntity.ok(orderService.getAllOrderById(id));
	}
	@PutMapping("/handle-return")
	public ResponseEntity<?>handleReturnOrder(@RequestParam int id,
			@RequestParam int status,
			@RequestParam String reason,
			@RequestParam String description
			){
		orderService.handleOrderReturn(id, status, reason,description);
		return ResponseEntity.ok().build();
	}
	
	

}
