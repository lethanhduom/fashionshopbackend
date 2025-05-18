package com.management.shopfashion.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;


import com.management.shopfashion.dto.ChatMessageDto;
import com.management.shopfashion.dto.UserDto;
import com.management.shopfashion.dto.response.ChatMessageInfoResponse;
import com.management.shopfashion.entity.ChatMessage;
import com.management.shopfashion.entity.Product;
import com.management.shopfashion.entity.User;
import com.management.shopfashion.mapper.ChatMessageMapper;
import com.management.shopfashion.mapper.UserMapper;
import com.management.shopfashion.repository.ChatMessageRepo;
import com.management.shopfashion.repository.ProductRepo;
import com.management.shopfashion.repository.UserRepo;
import com.management.shopfashion.service.ChatService;
import com.management.shopfashion.service.TimeService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ChatServiceImpl implements ChatService {
	  private ChatMessageRepo chatMessageRepository;
	  private UserRepo userRepository;
	  private ProductRepo productRepository;
	  private TimeService timeService;
	@Override
	public void saveMessage(ChatMessageDto chatMessageDto) {
		 	User sender = userRepository.findById(chatMessageDto.getSenderId()).orElseThrow();
	        User receiver = userRepository.findById(chatMessageDto.getReceiverId()).orElseThrow();
	        Product product = productRepository.findById(chatMessageDto.getProductId()).orElseThrow();

	        ChatMessage message = new ChatMessage();
	        message.setSender(sender);
	        message.setReceive(receiver);
	        message.setProduct(product);
	        message.setContent(chatMessageDto.getContent());
	        message.setDatetime(LocalDateTime.now());

	        chatMessageRepository.save(message);
		
	}
	@Override
	public List<ChatMessageDto> getChatHistory(int userId, int productId) {
	    User admin=userRepository.getAccountUser();
		int id_admin=admin.getId_user();
		List<ChatMessage>listChatMessage=chatMessageRepository.findChatWithAdmin(id_admin,userId, productId);
		return listChatMessage.stream().map(message->new ChatMessageDto(message.getSender().getId_user(),message.getReceive().getId_user(),message.getProduct().getId_product(),message.getContent(),message.getDatetime())).collect(Collectors.toList());
	}
	
	
	@Override
	public List<UserDto> getUsersChattedWithAdmin() {
		User admin=userRepository.getAccountUser();
		int id_admin=admin.getId_user();
		List<User>getListUser=userRepository.findAllById(chatMessageRepository.findUserIdsChattedWithAdmin(id_admin));
		  return getListUser.stream().map(user->UserMapper.mapUseDto(user)).collect(Collectors.toList());
	}
	@Override
	public List<ChatMessageInfoResponse> getInforUserChatWithAdmin() {
		User admin=userRepository.getAccountUser();
		int id_admin=admin.getId_user();
		List<ChatMessage>getListChatMessage=chatMessageRepository.findLatestMessagePerUserAndProductWithAdmin(id_admin);
		return getListChatMessage.stream().map(chat->ChatMessageMapper.MapChatmessageInforResponse(chat)).collect(Collectors.toList());
	}

}
