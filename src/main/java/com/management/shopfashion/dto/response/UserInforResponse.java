package com.management.shopfashion.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserInforResponse {
	private int id;
	private String name;
	private String phone;
	private String address;
	private String username;
	private String email;
}
