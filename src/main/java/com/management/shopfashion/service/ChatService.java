package com.management.shopfashion.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.management.shopfashion.dto.ChatMessageDto;
import com.management.shopfashion.dto.UserDto;
import com.management.shopfashion.dto.response.ChatMessageInfoResponse;
import com.management.shopfashion.entity.ChatMessage;
import com.management.shopfashion.entity.User;
import com.management.shopfashion.repository.ChatMessageRepo;

@Service
public interface ChatService {
	public void saveMessage(ChatMessageDto chatMessageDto);
	   public List<ChatMessageDto> getChatHistory(int userId, int productId);

	    public List<UserDto> getUsersChattedWithAdmin() ;
	    public List<ChatMessageInfoResponse>getInforUserChatWithAdmin();
}
