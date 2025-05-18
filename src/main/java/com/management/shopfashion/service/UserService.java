package com.management.shopfashion.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.management.shopfashion.dto.UserDto;
import com.management.shopfashion.dto.request.ChangePasswordRequest;
import com.management.shopfashion.dto.response.AuthenResponse;
import com.management.shopfashion.dto.response.UserInforResponse;

@Service
public interface  UserService {
	public UserDto signupAccount(UserDto user);
	public AuthenResponse signinAccount(UserDto userDto);
	String generateToken(UserDto userDto);
	public String generateVerificationCode();
	public String ActiveUser(String username,String code);
	public int getIdCart(String username);
	public UserInforResponse getInforUser(String username);
	public AuthenResponse signinAccountAdmin(UserDto userDto);
	public Page<UserDto>getUserAccounts(int page,int seze,String query);
	public UserDto createAccountByAdmin(UserDto userDto);
    public void UpdateInfoAccount(UserInforResponse userInfo);
    public void ChangePassword(ChangePasswordRequest changePassword);
    public void actionUser(int id);

}
