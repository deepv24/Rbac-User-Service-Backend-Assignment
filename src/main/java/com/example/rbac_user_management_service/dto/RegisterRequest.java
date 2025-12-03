package com.example.rbac_user_management_service.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {
    @NotBlank(message="Username is Required")
    private String username;
    @Email(message = "Email must be valid")
    @NotBlank(message = "Email is Required")
    private String email;
    @NotBlank(message = "Password is required")
    @Size(min=6,message = "Password must be at least 6 characters")
    private String password;
}
