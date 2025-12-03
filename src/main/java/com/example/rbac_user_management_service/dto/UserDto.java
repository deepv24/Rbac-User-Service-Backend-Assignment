package com.example.rbac_user_management_service.dto;

import lombok.*;

import java.time.Instant;
import java.util.Set;
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private long id;
    private String username;
    private String email;
    private Set<String> roles;
    private Instant lastLoginAt;

}
