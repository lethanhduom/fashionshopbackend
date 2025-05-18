package com.management.shopfashion.mapper;

import com.management.shopfashion.dto.response.ChatMessageInfoResponse;
import com.management.shopfashion.entity.ChatMessage;

public class ChatMessageMapper {
public static ChatMessageInfoResponse MapChatmessageInforResponse(ChatMessage chatMessage) {
	if(chatMessage==null)
		return null;
	ChatMessageInfoResponse chatInfo=new ChatMessageInfoResponse();
	chatInfo.setId_product(chatMessage.getProduct().getId_product());
	if(!chatMessage.getReceive().getRole().equals("admin")) {
		chatInfo.setId_user(chatMessage.getReceive().getId_user());
		chatInfo.setRole(chatMessage.getReceive().getRole());
		chatInfo.setFullname(chatMessage.getReceive().getFullname());
		chatInfo.setUsername(chatMessage.getReceive().getUsername());
		
	}else {
		chatInfo.setId_user(chatMessage.getSender().getId_user());
		chatInfo.setRole(chatMessage.getSender().getRole());
		chatInfo.setFullname(chatMessage.getSender().getFullname());
		chatInfo.setUsername(chatMessage.getSender().getUsername());
	}
	chatInfo.setProductName(chatMessage.getProduct().getNameProduct());
	return chatInfo;
}
}
