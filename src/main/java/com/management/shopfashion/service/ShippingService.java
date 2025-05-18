package com.management.shopfashion.service;

import org.springframework.stereotype.Service;

@Service
public interface ShippingService {
	public double[] getCoordinates(String address);
	public double getDistanceKm(String userAddress);
	public double calculateFee(double distanceKm);
	public int makeRounding(double value);
}
