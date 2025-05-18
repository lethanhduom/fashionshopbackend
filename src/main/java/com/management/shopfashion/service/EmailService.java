package com.management.shopfashion.service;

import org.springframework.stereotype.Service;

@Service
public interface EmailService {
	 public void sendVerificationEmail(String toEmail, String code);
	 public void sendVerificationChangePassword(String toEmail, String userName);
}
