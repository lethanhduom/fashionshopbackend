package com.management.shopfashion.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
	
   private int id_user;
   private String fullname;
   private String email;
   private String phone;
   private String role;
   private String username;
   private String password;
   private String createTime;
   private int status;
   private int id_cart;
   private String verificationCode;
   private String address;
   
}
