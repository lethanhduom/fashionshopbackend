package com.management.shopfashion.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.management.shopfashion.dto.request.QuestionRequest;
import com.management.shopfashion.dto.response.DataOllamaResponse;
import com.management.shopfashion.dto.response.DisplayProductResponse;
import com.management.shopfashion.dto.response.OllamaResponse;
import com.management.shopfashion.entity.Product;
import com.management.shopfashion.service.OllamaService;
import com.management.shopfashion.service.ProductService;

import jakarta.persistence.EntityManager;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("api/ai-query")
public class AIQueryController {

    private final OllamaService ollamaService;
    private final EntityManager entityManager;
    private final ProductService productService;

    @PostMapping("/ask")
    public ResponseEntity<OllamaResponse> searchProducts(@RequestBody QuestionRequest request) {
        try {
            // Bước 1: Gửi câu hỏi đến AI để phân tích
            OllamaResponse aiResponse = ollamaService.generateSQL(request.getQuestion());

            // Nếu không có SQL => chỉ trả lời tự nhiên (câu hỏi chung)
            if (aiResponse.getSql_query() == null || aiResponse.getSql_query().isBlank()) {
                return ResponseEntity.ok(aiResponse);
            }

            // Log SQL query để debug
            System.out.println("Executing SQL: " + aiResponse.getSql_query());

            // Bước 2: Có SQL => thực thi truy vấn
            List<Object[]> results = entityManager.createNativeQuery(aiResponse.getSql_query()).getResultList();
            
            // Kiểm tra nếu không có kết quả
            if (results.isEmpty()) {
                aiResponse.setResponse("Không tìm thấy sản phẩm nào phù hợp với yêu cầu của bạn.");
                return ResponseEntity.ok(aiResponse);
            }

            List<DataOllamaResponse> dataList = new ArrayList<>();

            for (Object[] row : results) {
                DataOllamaResponse data = new DataOllamaResponse();
                
                // Xử lý id_product (luôn có trong kết quả)
                int productId = ((Number) row[0]).intValue();
                Product product = productService.getProductById(productId);
                
                if (product == null) {
                    continue; // Bỏ qua nếu không tìm thấy product
                }
                
                DisplayProductResponse productDisplay = productService.convertProductDisplay(product);
                data.setDisplayProductResponse(productDisplay);

                // Xử lý image_url_color nếu có trong kết quả (cột thứ 2)
                if (row.length > 2 && row[2] != null) {
                    data.setImageUrlColor(row[1].toString());
                }

                dataList.add(data);
            }

            // Nếu có sản phẩm thì cập nhật response
            if (!dataList.isEmpty()) {
                aiResponse.setResponse("Đây là một số sản phẩm phù hợp với yêu cầu của bạn:");
                aiResponse.setProductResponse(dataList);
            } else {
                aiResponse.setResponse("Không tìm thấy sản phẩm nào phù hợp.");
            }

            return ResponseEntity.ok(aiResponse);

        } catch (Exception e) {
            e.printStackTrace(); // Log lỗi chi tiết
            return ResponseEntity.status(500).body(
                new OllamaResponse("Xin lỗi, có lỗi xảy ra khi xử lý yêu cầu của bạn. Chi tiết: " + 
                                  e.getMessage(), null, null, null)
            );
        }
    }
}