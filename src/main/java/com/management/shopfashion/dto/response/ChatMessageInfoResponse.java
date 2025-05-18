package com.management.shopfashion.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ChatMessageInfoResponse {
  private int id_user;
  private int id_product;
  private String role;
  private String fullname;
  private String username;
  private String productName;
}
