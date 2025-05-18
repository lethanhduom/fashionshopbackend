package com.management.shopfashion.service.impl;

import java.util.List;
import java.util.Collections;

import org.springframework.stereotype.Service;

import com.management.shopfashion.entity.Product;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

@Service
public class ProductQueryService {

    @PersistenceContext
    private EntityManager entityManager;

    public List<Product> queryProductsBySQL(String sql) {
        try {
            // Sửa tên bảng sai nếu AI viết "products" thay vì "product"
            sql = sql.replaceAll("(?i)\\bproducts\\b", "product");

            Query nativeQuery = entityManager.createNativeQuery(sql, Product.class);

            @SuppressWarnings("unchecked")
            List<Product> result = nativeQuery.getResultList();

            return result;
        } catch (Exception e) {
            System.out.println("SQL Error: " + e.getMessage());
            e.printStackTrace();
            return Collections.emptyList();
        }
    }
}
