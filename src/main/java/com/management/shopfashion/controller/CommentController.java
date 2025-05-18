package com.management.shopfashion.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpStatus;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.management.shopfashion.dto.CommentDto;
import com.management.shopfashion.dto.response.CommentResponse;
import com.management.shopfashion.service.CommentService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/api/comment")
public class CommentController {
	private CommentService commentService;

	@PostMapping("/create-comment")
	public ResponseEntity<?> createComment(@RequestBody CommentDto commentDto){
		commentService.createComment(commentDto);
		return ResponseEntity.ok().build();
	}
	@GetMapping("get-comment")
	public ResponseEntity<Page<CommentResponse>>getPageComment(
			@RequestParam int id,
			@RequestParam(defaultValue = "0") int page,
			@RequestParam (defaultValue = "5") int size
			){
		return ResponseEntity.ok(commentService.getPageComment(id, page, size));
	}
	@GetMapping("/summary")
	  public ResponseEntity<?> getEmotionSummary() {
        return ResponseEntity.ok(commentService.getEmotionSummary());
    }
	
	@GetMapping("/summary-product")
	  public ResponseEntity<?> getEmotionProductSummary() {
		List<Object[]> results=commentService.getEmotionByProduct();
		 Map<Integer, Map<String, Object>> groupedData = new HashMap<>();
		 for (Object[] row : results) {
		        Integer productId = (Integer) row[0];
		        Integer emotion = (Integer) row[1];
		        String productName = (String) row[2];
		        Long count = (Long) row[3];

		        Map<String, Object> productStats = groupedData.getOrDefault(productId, new HashMap<>());
		        productStats.put("productId", productId);
		        productStats.put("productName", productName);

		        switch (emotion) {
		            case 0 -> productStats.put("negative", count);
		            case 1 -> productStats.put("positive", count);
		            case 2 -> productStats.put("neutral", count);
		        }

		        groupedData.put(productId, productStats);
		    }


		    for (Map<String, Object> map : groupedData.values()) {
		        map.putIfAbsent("positive", 0L);
		        map.putIfAbsent("neutral", 0L);
		        map.putIfAbsent("negative", 0L);
		    }

      return ResponseEntity.ok(new ArrayList<>(groupedData.values()));
  }
	@GetMapping("/negative-alert")
	public ResponseEntity<?> getNegativeAlertProducts(@RequestParam(defaultValue = "3") Long threshold) {
	    return ResponseEntity.ok(commentService.getNegativeAlertProducts(threshold));
	}
	@GetMapping("/filter")
	public ResponseEntity<?> filterComments(
	        @RequestParam(required = false) Integer productId,
	        @RequestParam(required = false) Integer emotion) {
	    List<CommentResponse> comments = commentService.getCommentsByProductAndEmotion(productId, emotion);
	    return ResponseEntity.ok(comments);
	}

}
