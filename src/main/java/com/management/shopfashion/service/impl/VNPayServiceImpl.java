package com.management.shopfashion.service.impl;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.stereotype.Service;

import com.management.shopfashion.service.VNPayService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class VNPayServiceImpl implements VNPayService {
	public static  String vnp_Url="https://sandbox.vnpayment.vn/paymentv2/vpcpay.html";
	public static String vnp_TmnCode="6FULUY6X";
	public static String vnp_HashSecret="1V1WI5D2VSQ87VIG1B7AQROJC4WN9CTN";
	public static String vnp_ApiUrl="https://sandbox.vnpayment.vn/merchant_webapi/api/transaction";
	public static String vnp_ReturnUrl="http://localhost:3000/payment/vnpay";
		@Override
		public String md5(String message) {
			String digest = null;
	        try {
	            MessageDigest md = MessageDigest.getInstance("MD5");
	            byte[] hash = md.digest(message.getBytes("UTF-8"));
	            StringBuilder sb = new StringBuilder(2 * hash.length);
	            for (byte b : hash) {
	                sb.append(String.format("%02x", b & 0xff));
	            }
	            digest = sb.toString();
	        } catch (UnsupportedEncodingException ex) {
	            digest = "";
	        } catch (NoSuchAlgorithmException ex) {
	            digest = "";
	        }
	        return digest;
		}
		@Override
		public String Sha256(String message) {
			  String digest = null;
		        try {
		            MessageDigest md = MessageDigest.getInstance("SHA-256");
		            byte[] hash = md.digest(message.getBytes("UTF-8"));
		            StringBuilder sb = new StringBuilder(2 * hash.length);
		            for (byte b : hash) {
		                sb.append(String.format("%02x", b & 0xff));
		            }
		            digest = sb.toString();
		        } catch (UnsupportedEncodingException ex) {
		            digest = "";
		        } catch (NoSuchAlgorithmException ex) {
		            digest = "";
		        }
		        return digest;
		}
		@Override
		public String hashAllFields(Map fields) {
			 List fieldNames = new ArrayList(fields.keySet());
		        Collections.sort(fieldNames);
		        StringBuilder sb = new StringBuilder();
		        Iterator itr = fieldNames.iterator();
		        while (itr.hasNext()) {
		            String fieldName = (String) itr.next();
		            String fieldValue = (String) fields.get(fieldName);
		            if ((fieldValue != null) && (fieldValue.length() > 0)) {
		                sb.append(fieldName);
		                sb.append("=");
		                sb.append(fieldValue);
		            }
		            if (itr.hasNext()) {
		                sb.append("&");
		            }
		        }
		        return hmacSHA512(vnp_HashSecret,sb.toString());
		}
		@Override
		public String hmacSHA512(String key, String data) {
			 try {

		            if (key == null || data == null) {
		                throw new NullPointerException();
		            }
		            final Mac hmac512 = Mac.getInstance("HmacSHA512");
		            byte[] hmacKeyBytes = key.getBytes();
		            final SecretKeySpec secretKey = new SecretKeySpec(hmacKeyBytes, "HmacSHA512");
		            hmac512.init(secretKey);
		            byte[] dataBytes = data.getBytes(StandardCharsets.UTF_8);
		            byte[] result = hmac512.doFinal(dataBytes);
		            StringBuilder sb = new StringBuilder(2 * result.length);
		            for (byte b : result) {
		                sb.append(String.format("%02x", b & 0xff));
		            }
		            return sb.toString();

		        } catch (Exception ex) {
		            return "";
		        }
		}
		@Override
		public String getIpAddress(HttpServletRequest request) {
			 String ipAdress;
		        try {
		            ipAdress = request.getHeader("X-FORWARDED-FOR");
		            if (ipAdress == null) {
		                ipAdress = request.getRemoteAddr();
		            }
		        } catch (Exception e) {
		            ipAdress = "Invalid IP:" + e.getMessage();
		        }
		        return ipAdress;
		    }
		@Override
		public String getRandomNumber(int len) {
			   Random rnd = new Random();
		        String chars = "0123456789";
		        StringBuilder sb = new StringBuilder(len);
		        for (int i = 0; i < len; i++) {
		            sb.append(chars.charAt(rnd.nextInt(chars.length())));
		        }
		        return sb.toString();
		}

}
