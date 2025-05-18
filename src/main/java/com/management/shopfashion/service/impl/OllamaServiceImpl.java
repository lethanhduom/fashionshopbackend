package com.management.shopfashion.service.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.HttpServerErrorException;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.management.shopfashion.dto.response.OllamaResponse;
import com.management.shopfashion.service.OllamaService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class OllamaServiceImpl implements OllamaService {

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    
    // Timeout settings
    private static final int CONNECT_TIMEOUT = 5000; // 5 seconds
    private static final int READ_TIMEOUT = 100000000; // 10 seconds

    public OllamaServiceImpl() {
        this.restTemplate = new RestTemplate();
        this.objectMapper = new ObjectMapper();
        
        // Configure timeout for restTemplate
        ((org.springframework.http.client.SimpleClientHttpRequestFactory) restTemplate.getRequestFactory())
            .setConnectTimeout(CONNECT_TIMEOUT);
        ((org.springframework.http.client.SimpleClientHttpRequestFactory) restTemplate.getRequestFactory())
            .setReadTimeout(READ_TIMEOUT);
    }

    @Override
    public OllamaResponse generateSQL(String userMessage) {
        String url = "http://localhost:11434/api/generate";
        
        String prompt = """
        		[HỆ THỐNG TRỢ LÝ THỜI TRANG]
        		Bạn là trợ lý ảo thông minh cho cửa hàng thời trang. Nhiệm vụ của bạn:

        		1. PHÂN TÍCH YÊU CẦU:
        		   - Hiểu ý định khách hàng qua câu hỏi
        		   - Xác định loại sản phẩm, màu sắc, kích thước, giá cả, phong cách
        		   - Nếu câu hỏi là tổng quát hoặc tư vấn phong cách ("mặc gì cho sang", "sản phẩm nào nổi bật") → KHÔNG viết SQL, chỉ phản hồi tự nhiên, `sql_query` để là null

        		2. TẠO TRUY VẤN SQL:
        		   - Dựa trên schema:
        		     * product(id_product, name_product, description, price, rate, id_category, is_delete)
        		     * product_detail(id_product_detail, id_product, id_color, id_product_image, id_size, price, stock)
        		     * product_image(id_product_image, image_url, id_product)
        		     * color(id_color, name_color, code_color)
        		     * size(id_size, name_size)
        		     * category(id_category, name_cat)
        		   - Luôn lọc theo điều kiện của product: `is_delete = 0` (dùng alias nếu cần)
        		   - JOIN các bảng liên quan đúng mối quan hệ (ưu tiên JPA)
        		   - Thêm LIMIT 10
    			   - Nếu không có màu → chỉ SELECT `id_product`,`p.name_product`
        		   - Nếu câu hỏi có màu sắc → SELECT `id_product`, `p.name_product`,`image_url` (JOIN từ product_image)
        		   - KHÔNG dùng `code_color` để lọc màu, chỉ dùng `name_color`
        		   - Lọc phong cách như vintage, thanh lịch, năng động → dựa theo `description` hoặc `name_color` (gợi ý mapping: vintage → nâu, beige, xanh lá nhạt)
                   - Nếu chỉ hỏi về giá thì chỉ cần lọc trong bảng product
        		3. PHẢN HỒI TỰ NHIÊN:
        		   - Trả lời bằng tiếng Việt thân thiện
        		   - Nếu thiếu thông tin → đặt câu hỏi làm rõ
        		   - Gợi ý sản phẩm phù hợp với yêu cầu

        		[FORMAT OUTPUT]
        		{
        		  "response": "Câu trả lời tự nhiên",
        		  "sql_query": "Truy vấn SQL (nếu có)",
        		  "need_clarification": "Câu hỏi làm rõ (nếu cần)"
        		}

        		Yêu cầu khách hàng: "%s"
        		""".formatted(userMessage);

        
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("model", "llama3");
        requestBody.put("prompt", prompt);
        requestBody.put("format", "json");
        requestBody.put("stream", false);
        requestBody.put("options", Map.of(
            "temperature", 0.7,
            "top_p", 0.9
        ));

        try {
            log.info("Sending request to Ollama API with prompt: {}", prompt);
            
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);
            
            ResponseEntity<String> response = restTemplate.postForEntity(url, entity, String.class);
            
            log.debug("Received response from Ollama: {}", response.getBody());
            
            JsonNode root = objectMapper.readTree(response.getBody());
            String jsonResponse = root.path("response").asText();
            
            OllamaResponse ollamaResponse = objectMapper.readValue(jsonResponse, OllamaResponse.class);
            
            // Validate response
            if (ollamaResponse.getResponse() == null || ollamaResponse.getResponse().isBlank()) {
                throw new RuntimeException("Empty response from Ollama");
            }
            
            return ollamaResponse;
            
        } catch (ResourceAccessException e) {
            log.error("Connection error to Ollama API: {}", e.getMessage());
            return new OllamaResponse(
                "Xin lỗi, hệ thống đang bận. Vui lòng thử lại sau.", 
                null, 
                null,null
            );
        } catch (HttpClientErrorException | HttpServerErrorException e) {
            log.error("Ollama API error: {} - {}", e.getStatusCode(), e.getResponseBodyAsString());
            return new OllamaResponse(
                "Xin lỗi, có lỗi khi xử lý yêu cầu của bạn. Mã lỗi: " + e.getStatusCode(),
                null,
                null ,null
            );
        } catch (Exception e) {
            log.error("Unexpected error: {}", e.getMessage(), e);
            return new OllamaResponse(
                "Xin lỗi, tôi gặp chút khó khăn. Bạn có thể cho biết thêm chi tiết về sản phẩm bạn muốn tìm?",
                null,
               null,null
            );
        }
    }
}
