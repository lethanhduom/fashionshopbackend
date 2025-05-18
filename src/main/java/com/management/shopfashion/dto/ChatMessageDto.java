package com.management.shopfashion.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ChatMessageDto {
	 private int senderId;
	    private int receiverId;
	    private int productId;
	    private String content;
	    private LocalDateTime datetime;
}
