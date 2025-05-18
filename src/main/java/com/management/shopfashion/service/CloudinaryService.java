package com.management.shopfashion.service;

import java.io.IOException;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public interface CloudinaryService {
	public String uploadImage(MultipartFile file)throws IOException ;
}
