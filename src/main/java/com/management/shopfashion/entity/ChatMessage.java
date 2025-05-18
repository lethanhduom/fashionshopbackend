package com.management.shopfashion.entity;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "chatMessage")
public class ChatMessage {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id_chat")
	private int id_chat;
	@ManyToOne
	@JoinColumn(name = "id_sender",referencedColumnName = "id_user")
	private User sender;
	@ManyToOne
	@JoinColumn(name = "id_receiver",referencedColumnName = "id_user")
	private User receive;
    @ManyToOne
    @JoinColumn(name = "id_product",referencedColumnName = "id_product")
    private Product product;
	@Column(name="id_content")
	private String content;
	@Column(name="date_time",columnDefinition = "Longtext")
	private LocalDateTime datetime;
	
	
}
