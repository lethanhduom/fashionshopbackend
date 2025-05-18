package com.management.shopfashion.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.management.shopfashion.dto.CartDto;
import com.management.shopfashion.entity.Cart;
import com.management.shopfashion.entity.User;
import com.management.shopfashion.mapper.CartMapper;
import com.management.shopfashion.repository.UserRepo;
import com.management.shopfashion.service.CartService;

@Component
public class AdminInitializer implements CommandLineRunner {
	
	@Autowired
	private UserRepo userRepo;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
    private  CartService cartService;
	@Override
	public void run(String... args) throws Exception {
	boolean adminExit=userRepo.findByRole("admin").isPresent();
	
	if(!adminExit) {
		CartDto cartDto = new CartDto();
		Cart cart = CartMapper.MapCart(cartDto);
		User admin=new User();
		admin.setFullname("admin");
		admin.setUsername("admin");
		admin.setAddress("HCM");
		admin.setRole("admin");
		admin.setCart(cart);
		admin.setPassword(passwordEncoder.encode("admin123"));
		admin.setStatus(1);
		userRepo.save(admin);
		System.out.print("Tài khoản admin mặc định đã được tạo");
		
	}else {
		System.out.print("Đã có tài khoản admin trong hệ thống");
	}
		
	}

}
