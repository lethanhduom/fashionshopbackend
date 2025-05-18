package com.management.shopfashion.mapper;

import com.management.shopfashion.dto.UserDto;
import com.management.shopfashion.entity.User;

public class UserMapper {
	public static UserDto mapUseDto(User user) {
		if(user==null)
			return null;
		UserDto userDto=new UserDto();
		userDto.setUsername(user.getUsername());
		userDto.setPassword(user.getPassword());
		userDto.setId_user(user.getId_user());
		userDto.setEmail(user.getEmail());
		userDto.setFullname(user.getFullname());
		userDto.setPhone(user.getPhone());
		userDto.setRole(user.getRole());
		userDto.setCreateTime(user.getCreateTime());
		userDto.setStatus(user.getStatus());
		userDto.setVerificationCode(user.getVerificationCode());
		userDto.setId_cart(user.getCart().getId_cart());
		userDto.setAddress(user.getAddress());
		return userDto;
	}
	public static User mapUser(UserDto userDto) {
		if(userDto==null)
			return null;
		User user=new User();
		user.setUsername(userDto.getUsername());
		user.setPassword(userDto.getPassword());
		user.setId_user(userDto.getId_user());
		user.setEmail(userDto.getEmail());
		user.setFullname(userDto.getFullname());
		user.setPhone(userDto.getPhone());
		user.setRole(userDto.getRole());
		user.setCreateTime(userDto.getCreateTime());
		user.setStatus(userDto.getStatus());
		user.setVerificationCode(userDto.getVerificationCode());
		user.setAddress(userDto.getAddress());
		return user;

		
	}
}
