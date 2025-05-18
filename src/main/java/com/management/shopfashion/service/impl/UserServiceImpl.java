package com.management.shopfashion.service.impl;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.StringJoiner;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.management.shopfashion.dto.CartDto;
import com.management.shopfashion.dto.UserDto;
import com.management.shopfashion.dto.request.ChangePasswordRequest;
import com.management.shopfashion.dto.response.AuthenResponse;
import com.management.shopfashion.dto.response.UserInforResponse;
import com.management.shopfashion.entity.Cart;
import com.management.shopfashion.entity.User;
import com.management.shopfashion.exception.ErrorCode;
import com.management.shopfashion.exception.UserAlreadyExistsException;
import com.management.shopfashion.mapper.UserMapper;
import com.management.shopfashion.repository.CartRepo;
import com.management.shopfashion.repository.UserRepo;
import com.management.shopfashion.service.CartService;
import com.management.shopfashion.service.EmailService;
import com.management.shopfashion.service.TimeService;
import com.management.shopfashion.service.UserService;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSObject;
import com.nimbusds.jose.KeyLengthException;
import com.nimbusds.jose.Payload;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jwt.JWTClaimsSet;

import lombok.AllArgsConstructor;
import lombok.experimental.NonFinal;
@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
	private UserRepo userRepo;
	private PasswordEncoder passwordEncoder;
	private final EmailService mailService;
	private CartService cartService;
	private TimeService timeService;
	private CartRepo cartRepo;

	@NonFinal
	protected static final String SIGNER_KEY="tX/nQ+evyqQdmSa9jxVjHyAEQ4Svb++/gxm1hVkfOaYzlxOx0M2wtxI45lUWk1Hz";
	
	@Override
	public UserDto signupAccount(UserDto userDto) {
	   if(userRepo.findByUsername(userDto.getUsername())!=null) {
		   throw new UserAlreadyExistsException("USERNAME EXIT");
	   }else if(userRepo.findByEmail(userDto.getEmail())!=null){
		   throw new UserAlreadyExistsException("EMAIL EXIT");
	   } else {
		   String code=generateVerificationCode();		   
		   userDto.setVerificationCode(code);
		   userDto.setRole("user");
		   userDto.setCreateTime(timeService.getCurrentVietnamTimeFormatted());
		   HashSet<String>scope=new HashSet<>();
			User user=UserMapper.mapUser(userDto);
			user.setPassword(passwordEncoder.encode(userDto.getPassword()));
			CartDto cartDto=new CartDto();
			Cart cart=cartService.createCart(cartDto);
			user.setCart(cart);
			user.setAddress(userDto.getAddress());
			User signUser=userRepo.save(user);
			mailService.sendVerificationEmail(userDto.getEmail(), code);
			return UserMapper.mapUseDto(signUser);
	   }
		
	}

	@Override
	public AuthenResponse signinAccount(UserDto userDto) {
		User user=userRepo.findByUsername(userDto.getUsername());
		if(user!=null) {
			if(user.getStatus()!=0) {
				if(passwordEncoder.matches(userDto.getPassword(), user.getPassword())) {
					var token=generateToken(UserMapper.mapUseDto(user));
					return AuthenResponse.builder()
							.token(token)
							.error(null)
							.isAuthenticated(true)
							.id_cart(user.getCart().getId_cart())
							.build();
				}else {
					return new AuthenResponse(null,ErrorCode.PASSWORD_NOT_MATCH.getMessage(),false,0);
				}
			}else {
				return new AuthenResponse(null,ErrorCode.ACCOUNT_NOT_ACTIVE.getMessage(),false,0);
			}
			
		}else {
			return new AuthenResponse(null,ErrorCode.USER_NOT_EXITED.getMessage(),false,0);
		}
		
	}
	
	
	@Override
	public String generateToken(UserDto userDto) {
		JWSHeader header=new JWSHeader(JWSAlgorithm.HS512);
		JWTClaimsSet jwtClaimSet=new JWTClaimsSet.Builder()
				.subject(userDto.getUsername())
			
				.issuer("fashion.com")
				.issueTime(new Date())
				.expirationTime(new Date(Instant.now().plus(1, ChronoUnit.HOURS).toEpochMilli()))
				.claim("scope",userDto.getRole())
				
				.build();
		Payload payLoad=new Payload(jwtClaimSet.toJSONObject());
		JWSObject jwsObject=new JWSObject(header, payLoad);
		try {
			jwsObject.sign(new MACSigner(SIGNER_KEY.getBytes()));
			return jwsObject.serialize() ;
		} catch (KeyLengthException e) {
		
			e.printStackTrace();
		} catch (JOSEException e) {
			
			e.printStackTrace();
		}
		return null;
		
	}

	@Override
	public String generateVerificationCode() {
		  int code = 100000 + new Random().nextInt(900000);
		    return String.valueOf(code);
	}

	@Override
	public String ActiveUser(String username,String code) {
		try {
			User user=userRepo.findByUsername(username);
			if(user.getVerificationCode().equals(code)) {
				user.setStatus(1);
				User userUpdate= userRepo.save(user);
			
				return "Active Successful";
			}else {
				cartRepo.delete(user.getCart());
				userRepo.delete(user);
				
				return "Invalid Code ";
				
			}
			
		}
		catch(Exception e){
			e.printStackTrace();
			return "Active Failed";
		}
		
		
	}

	@Override
	public int getIdCart(String username) {
		int id_cart=userRepo.getUser(username).getCart().getId_cart();
		return id_cart;
	}

	@Override
	public UserInforResponse getInforUser(String username) {
		User user=userRepo.getUser(username);
		UserInforResponse userResponse=new UserInforResponse();
		userResponse.setName(user.getFullname());
		userResponse.setId(user.getId_user());
		userResponse.setPhone(user.getPhone());
		userResponse.setAddress(user.getAddress());
		userResponse.setUsername(user.getUsername());
		userResponse.setEmail(user.getEmail());
		return userResponse;
	}

	@Override
	public AuthenResponse signinAccountAdmin(UserDto userDto) {
		User user=userRepo.findByUsername(userDto.getUsername());
		if(user!=null&&user.getRole().equals("admin")) {
			if(user.getStatus()!=0) {
				if(passwordEncoder.matches(userDto.getPassword(), user.getPassword())) {
					var token=generateToken(UserMapper.mapUseDto(user));
					return AuthenResponse.builder()
							.token(token)
							.error(null)
							.isAuthenticated(true)
							.id_cart(user.getCart().getId_cart())
							.build();
				}else {
					return new AuthenResponse(null,ErrorCode.PASSWORD_NOT_MATCH.getMessage(),false,0);
				}
			}else {
				return new AuthenResponse(null,ErrorCode.ACCOUNT_NOT_ACTIVE.getMessage(),false,0);
			}
			
		}else {
			return new AuthenResponse(null,ErrorCode.USER_NOT_EXITED.getMessage(),false,0);
		}
	
	}

	@Override
	public Page<UserDto> getUserAccounts(int page,int size,String query) {
		  Pageable pageable = PageRequest.of(page, size);
		    Page<User> userPage;

		    if (query == null || query.trim().isEmpty()) {
		        userPage = userRepo.getUserAccounts(pageable); // không tìm kiếm
		    } else {
		        userPage = userRepo.searchUserAccounts(query, pageable); // có từ khóa tìm kiếm
		    }

		    return userPage.map(UserMapper::mapUseDto);
	}

	@Override
	public UserDto createAccountByAdmin(UserDto userDto) {
		
		 if(userRepo.findByUsername(userDto.getUsername())!=null) {
			   throw new UserAlreadyExistsException("USERNAME EXIT");
		   }else if(userRepo.findByEmail(userDto.getEmail())!=null){
			   throw new UserAlreadyExistsException("EMAIL EXIT");
		   } else {
			   String code=generateVerificationCode();		   
			   userDto.setVerificationCode(code);
			   userDto.setRole("user");
			   userDto.setStatus(1);
			   userDto.setCreateTime(timeService.getCurrentVietnamTimeFormatted());
			   HashSet<String>scope=new HashSet<>();
				User user=UserMapper.mapUser(userDto);
				user.setPassword(passwordEncoder.encode(userDto.getPassword()));
				CartDto cartDto=new CartDto();
				Cart cart=cartService.createCart(cartDto);
				user.setCart(cart);
				User signUser=userRepo.save(user);
	
				return UserMapper.mapUseDto(signUser);
		   }
	}

	@Override
	public void UpdateInfoAccount(UserInforResponse userInfo) {
		
		User user=userRepo.getById(userInfo.getId());
		user.setAddress(userInfo.getAddress());
		user.setFullname(userInfo.getName());
		user.setUsername(userInfo.getUsername());
		userRepo.save(user);
		
	}

	@Override
	public void ChangePassword(ChangePasswordRequest changePassword) {
		User user=userRepo.findByUsername(changePassword.getUsername());
		if(user==null) {
			 throw new UsernameNotFoundException("Account not exits");
		}
		if (!passwordEncoder.matches(changePassword.getOldPassword(), user.getPassword())) {
            throw new IllegalArgumentException("The old password is incorrect");
        }
		String hashedNewPassword = passwordEncoder.encode(changePassword.getNewPassword());
		user.setPassword(hashedNewPassword);
		userRepo.save(user);
		mailService.sendVerificationChangePassword(user.getEmail(),user.getFullname());
	}

	@Override
	public void actionUser(int id) {
	User user=userRepo.getById(id);
	int status=user.getStatus()==0?1:0;
	user.setStatus(status);
	userRepo.save(user);
	}

	

	

}
 