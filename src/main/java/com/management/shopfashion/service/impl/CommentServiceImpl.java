package com.management.shopfashion.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.management.shopfashion.dto.CommentDto;
import com.management.shopfashion.dto.response.CommentResponse;
import com.management.shopfashion.entity.Comment;
import com.management.shopfashion.mapper.CommentMapper;
import com.management.shopfashion.repository.CommentRepo;
import com.management.shopfashion.repository.ProductDetailRepo;
import com.management.shopfashion.repository.ProductRepo;
import com.management.shopfashion.repository.UserRepo;
import com.management.shopfashion.service.CommentService;
import com.management.shopfashion.service.TimeService;

import lombok.AllArgsConstructor;
@Service
@AllArgsConstructor
public class CommentServiceImpl implements CommentService {
	private ProductDetailRepo productDetailRepo;
	private UserRepo userRepo;
	private CommentRepo commentRepo;
	private TimeService timeService;
	@Override
	public void createComment(CommentDto commentDto) {
	Comment comment=new Comment();
	comment.setProductDetail(productDetailRepo.getProductDetailById(commentDto.getId_product_detail()));
	comment.setUser(userRepo.getUser(commentDto.getUsername()));
	comment.setRate(commentDto.getRate());
	comment.setReview(commentDto.getReview());
	comment.setDateCreate(timeService.getCurrentVietnamTimeFormatted());
	comment.setEmotion(commentDto.getEmotion());
	
	commentRepo.save(comment);
	
		
	}
	@Override
	public Page<CommentResponse> getPageComment(int id, int page, int size) {
		Page<Comment>getComments=commentRepo.getPageComment(id, PageRequest.of(page, size));
		
		return getComments.map(comment->CommentMapper.MapCommentResponse(comment));
	}
	@Override
	public List<Object[]> getEmotionSummary() {
		
		return commentRepo.countByEmotion();
	}
	@Override
	public List<Object[]> getEmotionByProduct() {
	return commentRepo.countEmotionByProduct();
	}
	@Override
	public List<Map<String, Object>> getNegativeAlertProducts(long threshold) {
		 List<Object[]> result = commentRepo.findProductsWithNegativeComments(threshold);
		    List<Map<String, Object>> list = new ArrayList<>();

		    for (Object[] row : result) {
		        Map<String, Object> map = new HashMap<>();
		        map.put("productId", row[0]);
		        map.put("productName", row[1]);
		        map.put("negativeCount", row[2]);
		        list.add(map);
		    }
		    return list;
	}
	@Override
	public List<CommentResponse> getCommentsByProductAndEmotion(Integer productId, Integer emotion) {
		List<Comment>getComment=commentRepo.findCommentsByProductAndEmotion(productId, emotion);
		
		return getComment.stream().map(c->CommentMapper.MapCommentResponse(c)).collect(Collectors.toList());
	}

}
