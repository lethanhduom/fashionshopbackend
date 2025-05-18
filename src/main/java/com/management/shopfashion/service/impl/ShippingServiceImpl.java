package com.management.shopfashion.service.impl;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.management.shopfashion.service.ShippingService;

@Service
public class ShippingServiceImpl implements ShippingService {

    private final RestTemplate restTemplate = new RestTemplate();

    
    // @Value("${ors.api.key}")
    private String orsApiKey = "5b3ce3597851110001cf6248843de917070349068dcad67ce3e0ab94";

    // @Value("${store.address}")
    private String storeAddress = "Ho Chi Minh City, Vietnam";

    /**
     * Lấy toạ độ từ địa chỉ bằng Nominatim
     */
    @Override
    public double[] getCoordinates(String address) {
        try {
            String encoded = URLEncoder.encode(address, StandardCharsets.UTF_8);
            String url = "https://nominatim.openstreetmap.org/search?format=json&q=" + encoded;

            HttpHeaders headers = new HttpHeaders();
            headers.set("User-Agent", "SpringBootApp"); // bắt buộc

            HttpEntity<String> entity = new HttpEntity<>(headers);
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

            JSONArray results = new JSONArray(response.getBody());

            if (results.length() == 0) {
                System.err.println("Không tìm thấy toạ độ cho địa chỉ: " + address);
                return null;
            }

            JSONObject location = results.getJSONObject(0);
            double lat = location.getDouble("lat");
            double lon = location.getDouble("lon");

            return new double[]{lon, lat}; // ORS yêu cầu [longitude, latitude]
        } catch (Exception e) {
            System.err.println("Lỗi khi lấy toạ độ: " + e.getMessage());
            return null;
        }
    }

    /**
     * Tính khoảng cách giữa cửa hàng và người dùng bằng ORS (đơn vị km)
     */
    @Override
    public double getDistanceKm(String userAddress) {
        double[] from = getCoordinates(storeAddress);
        double[] to = getCoordinates(userAddress);

        if (from == null || to == null) {
            System.err.println("Không thể tính khoảng cách do thiếu tọa độ.");
            return 0;
        }

        try {
            String url = "https://api.openrouteservice.org/v2/matrix/driving-car";

            JSONObject body = new JSONObject();
            JSONArray locations = new JSONArray();
            locations.put(new JSONArray(from)); // store
            locations.put(new JSONArray(to));   // user
            body.put("locations", locations);
            body.put("metrics", new JSONArray().put("distance"));
            body.put("units", "km");

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("Authorization", orsApiKey);

            HttpEntity<String> request = new HttpEntity<>(body.toString(), headers);
            String response = restTemplate.postForObject(url, request, String.class);
            JSONObject json = new JSONObject(response);

            double distance = json.getJSONArray("distances").getJSONArray(0).getDouble(1);

            System.out.println("Khoảng cách từ cửa hàng đến '" + userAddress + "': " + distance + " km");
            return distance;
        } catch (Exception e) {
            System.err.println("Lỗi khi tính khoảng cách: " + e.getMessage());
            return 0;
        }
    }

    /**
     * Tính phí ship dựa trên khoảng cách
     */
    @Override
    public double calculateFee(double distanceKm) {
        double baseFee = 15000; // phí cơ bản
        double perKm = 20;    // mỗi km sau 5km

        return makeRounding( distanceKm <= 5 ? baseFee : baseFee + (distanceKm - 5) * perKm);
    }
    @Override
	public int makeRounding(double value) {
		if (value % 1 == 0) {
            int intValue = (int) value;
            if (intValue % 5000 == 0) return intValue;
            return ((intValue / 5000) + 1) * 5000;
        }

        // Nếu là số có phần thập phân
        int intValue = (int) value;
        int remainder = intValue % 1000;

        if (remainder < 500) {
            return intValue - remainder + 500;
        } else {
            return intValue - remainder + 1000;
        }
	}

}
