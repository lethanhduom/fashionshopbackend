package com.management.shopfashion.service.impl;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Service;

import com.management.shopfashion.service.TimeService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class TimeServiceImpl implements TimeService {

	@Override
	public String getCurrentVietnamTimeFormatted() {
		 ZonedDateTime now = ZonedDateTime.now(ZoneId.of("Asia/Ho_Chi_Minh"));
	        return now.format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"));
	}

}
