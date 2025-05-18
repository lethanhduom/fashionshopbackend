package com.management.shopfashion.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.management.shopfashion.dto.response.ChatMessageInfoResponse;
import com.management.shopfashion.entity.ChatMessage;
import com.management.shopfashion.service.ChatService;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
@RestController
@RequestMapping("/api/chat")
@RequiredArgsConstructor
public class ChatController {
    private final ChatService chatService;

    @GetMapping("/history")
    public ResponseEntity<?> getChatHistory(@RequestParam int userId,
                                            @RequestParam int productId) {
        return ResponseEntity.ok(chatService.getChatHistory(userId, productId));
    }

    @GetMapping("/users")
    public ResponseEntity<?> getAllUsersChattedWithAdmin() {
        return ResponseEntity.ok(chatService.getUsersChattedWithAdmin());
    
}
    @GetMapping("/get-users")
    public ResponseEntity<List<ChatMessageInfoResponse>>getUserChatWithAdmin(){
    	return ResponseEntity.ok(chatService.getInforUserChatWithAdmin());
    }
}
