package com.management.shopfashion.service;

import org.springframework.stereotype.Service;

import com.management.shopfashion.dto.response.OllamaResponse;

import io.swagger.v3.oas.annotations.servers.Server;

@Service
public interface OllamaService {
	 public OllamaResponse generateSQL(String question);
}
