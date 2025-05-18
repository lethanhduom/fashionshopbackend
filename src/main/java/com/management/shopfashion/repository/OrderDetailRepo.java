package com.management.shopfashion.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.management.shopfashion.entity.OrderDetail;

public interface OrderDetailRepo extends JpaRepository<OrderDetail, Integer> {

}
