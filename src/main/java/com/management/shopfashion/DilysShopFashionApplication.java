package com.management.shopfashion;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;


@SpringBootApplication(scanBasePackages = "com.management.shopfashion")
@EnableScheduling
public class DilysShopFashionApplication {

	public static void main(String[] args) {
		SpringApplication.run(DilysShopFashionApplication.class, args);
	}

}
