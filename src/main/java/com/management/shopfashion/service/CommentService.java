package com.management.shopfashion.service;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.management.shopfashion.dto.CommentDto;
import com.management.shopfashion.dto.response.CommentResponse;

@Service
public interface CommentService  {
public void createComment(CommentDto commentDto);
public Page<CommentResponse>getPageComment(int id,int page,int size);
public List<Object[]> getEmotionSummary();
public List<Object[]> getEmotionByProduct();
public List<Map<String, Object>> getNegativeAlertProducts(long threshold);
public List<CommentResponse> getCommentsByProductAndEmotion(Integer productId, Integer emotion);
}
