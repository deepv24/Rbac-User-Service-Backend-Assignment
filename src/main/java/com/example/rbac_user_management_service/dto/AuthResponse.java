package com.example.rbac_user_management_service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponse {
   private String token;
   private Long userId;
   private String email;
   private Set<String> roles;

}
