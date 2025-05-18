package com.management.shopfashion.mapper;

import com.management.shopfashion.dto.response.CommentResponse;
import com.management.shopfashion.entity.Comment;

public class CommentMapper {
public static CommentResponse MapCommentResponse(Comment comment) {
	if(comment==null)
		return null;
	CommentResponse commentResponse=new CommentResponse();
	commentResponse.setRate(comment.getRate());
	commentResponse.setUsername(comment.getUser().getUsername());
	commentResponse.setColor(comment.getProductDetail().getColor().getNameColor());
	commentResponse.setSize(comment.getProductDetail().getSize().getNameSize());
	commentResponse.setDate(comment.getDateCreate());
	commentResponse.setId_detail_product(comment.getProductDetail().getId_product_detail());
	commentResponse.setReview(comment.getReview());
	commentResponse.setEmotion(comment.getEmotion());
	commentResponse.setNamProduct(comment.getProductDetail().getProduct().getNameProduct());
	return commentResponse;
	
}


}