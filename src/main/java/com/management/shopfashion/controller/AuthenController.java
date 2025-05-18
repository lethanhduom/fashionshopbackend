package com.management.shopfashion.controller;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.management.shopfashion.dto.UserDto;
import com.management.shopfashion.dto.request.ChangePasswordRequest;
import com.management.shopfashion.dto.response.AuthenResponse;
import com.management.shopfashion.dto.response.UserInforResponse;
import com.management.shopfashion.service.UserService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("api/auth")
public class AuthenController {
	private UserService userService;
	@PostMapping("/sign-up")
	public ResponseEntity<UserDto>createUser(@RequestBody UserDto userDto){
		UserDto saveUser=userService.signupAccount(userDto);
		return new ResponseEntity<>(saveUser,HttpStatus.CREATED);
	}
	@PostMapping("/sign-in")
	public ResponseEntity<AuthenResponse>login(@RequestBody UserDto userDto){
		AuthenResponse authen=userService.signinAccount(userDto);
		return ResponseEntity.ok(authen);
	}
	@PostMapping("/active")
	public ResponseEntity<String>vertification(@RequestParam String username,@RequestParam String code){
		String activeResponse=userService.ActiveUser(username,code);
		return ResponseEntity.ok(activeResponse);
	}
	@GetMapping("/id-cart")
	public ResponseEntity<Integer>getIdCart(@RequestParam String username){
		return ResponseEntity.ok( userService.getIdCart(username));
	}
	@GetMapping("/infor-user")
	public ResponseEntity<UserInforResponse> getUser(@RequestParam String username){
		return ResponseEntity.ok(userService.getInforUser(username));
	}
	@PostMapping("/signin-admin")
	public ResponseEntity<AuthenResponse>loginAdmin(@RequestBody UserDto userDto){
		AuthenResponse authen=userService.signinAccountAdmin(userDto);
		return ResponseEntity.ok(authen);
	}
	@GetMapping("/get-accounts")
	public ResponseEntity<Page<UserDto>>getPageUser(
			@RequestParam(defaultValue = "0") int page,
			@RequestParam (defaultValue = "10") int size,
			 @RequestParam(required = false) String keyword
			){
		return ResponseEntity.ok(userService.getUserAccounts(page, size,keyword));
		}
	@PostMapping("/create-account")
	public ResponseEntity<UserDto>createAccount(@RequestBody UserDto userDto){
		return ResponseEntity.ok(userService.createAccountByAdmin(userDto));
	}
	@PutMapping("/update-account")
	public ResponseEntity<?>updateInfoAccount(@RequestBody UserInforResponse userInfo){
	userService.UpdateInfoAccount(userInfo);
	return ResponseEntity.ok().build();
	}
	@PutMapping("/change-password")
	public ResponseEntity<?>ChangePassword(@RequestBody ChangePasswordRequest changePassword){
		userService.ChangePassword(changePassword);
		return ResponseEntity.ok().build();
	}
	@PutMapping("/action")
	public ResponseEntity<?>action(@RequestParam int id){
		userService.actionUser(id);
		return ResponseEntity.ok().build();
	}
	
}
