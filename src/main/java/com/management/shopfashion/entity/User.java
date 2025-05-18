package com.management.shopfashion.entity;

import java.sql.Time;
import java.util.List;

import org.springframework.lang.Nullable;

import jakarta.annotation.Generated;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table (name = "user")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_user")
   private int id_user;
	@Column(name = "fullname")
   private String fullname;
	@Column(name = "email")
   private String email;
	@Column(name = "phone")
   private String phone;
	@Column(name = "role")
   private String role;
	@Column(name = "username")
   private String username;
	@Column(name = "password")
   private String password;
	@Column(name = "create_time")
   private String createTime;
	@Column(name = "status") // 0 chưa kích hoạt 1 đã kích hoạt 2 tạm thời khóa
   private int status;
	@Column(name="verification_code")
	private String verificationCode;
	@Column (name = "address")
	private String address;
	@OneToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	@JoinColumn(name = "id_cart",referencedColumnName = "id_cart")
	@Nullable
	private Cart cart;
	@OneToMany(mappedBy = "user")
	private List<WishList>wishLists;
	@OneToMany(mappedBy = "user")
	private List<Order>orders;
	@OneToMany(mappedBy = "user")
	private List<OrderReturn>orderReturnDetails;
	@OneToMany(mappedBy = "user")
	private List<Comment>comments;
}
