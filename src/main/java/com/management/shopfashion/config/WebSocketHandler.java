package com.management.shopfashion.config;

import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.management.shopfashion.dto.ChatMessageDto;
import com.management.shopfashion.entity.ChatMessage;
import com.management.shopfashion.service.ChatService;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Getter
@Setter
@Component
@RequiredArgsConstructor
public class WebSocketHandler extends TextWebSocketHandler {
	
	
	 private final ChatService chatService;
	 private final ObjectMapper objectMapper = new ObjectMapper();
	 private final Map<Integer, WebSocketSession> sessions = new ConcurrentHashMap<>();

	 @Override
	    public void afterConnectionEstablished(WebSocketSession session) {
	        System.out.println("WebSocket kết nối: " + session.getId());
	   
	        Integer userId = getUserIdFromSession(session);
	        if (userId != null) {
	            sessions.put(userId, session);
	            System.out.println("User " + userId + " đã kết nối WebSocket.");
	        }
	    }

	    @Override
	    public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
	        String payload = message.getPayload();
	        ChatMessageDto dto = objectMapper.readValue(payload, ChatMessageDto.class);

	        // Lưu vào DB
	        chatService.saveMessage(dto);

	        // Gửi tới người nhận nếu đang online
	        WebSocketSession receiverSession = sessions.get(dto.getReceiverId());
	        if (receiverSession != null && receiverSession.isOpen()) {
	            receiverSession.sendMessage(new TextMessage(objectMapper.writeValueAsString(dto)));
	        }

	        // Gửi lại cho người gửi luôn (để hiển thị realtime phía họ)
	        session.sendMessage(new TextMessage(objectMapper.writeValueAsString(dto)));
	    }

	    @Override
	    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
	        Integer userId = getUserIdFromSession(session);
	        if (userId != null) {
	            sessions.remove(userId);
	            System.out.println("User " + userId + " đã ngắt kết nối.");
	        }
	    }

	    @Override
	    public void handleTransportError(WebSocketSession session, Throwable exception) {
	        System.err.println("Lỗi kết nối WebSocket: " + exception.getMessage());
	    }

	    private Integer getUserIdFromSession(WebSocketSession session) {
	        try {
	            String query = session.getUri().getQuery(); // Lấy query string từ URI
	            if (query != null && !query.isEmpty()) {
	                // Tách query thành các tham số
	                String[] params = query.split("&");
	                for (String param : params) {
	                    if (param.startsWith("userId=")) {
	                        // Tách và trả về giá trị của userId
	                        String userIdStr = param.split("=")[1];
	                        try {
	                            return Integer.parseInt(userIdStr); // Chuyển đổi sang Integer
	                        } catch (NumberFormatException e) {
	                            System.err.println("Lỗi: userId không phải là số hợp lệ: " + userIdStr);
	                            return null;
	                        }
	                    }
	                }
	            }
	        } catch (Exception e) {
	            System.err.println("Lỗi khi phân tích query string từ URI: " + e.getMessage());
	            e.printStackTrace();
	        }
	        System.err.println("Không tìm thấy tham số userId trong query string.");
	        return null; // Trả về null nếu không tìm thấy userId hoặc gặp lỗi
	    }

}