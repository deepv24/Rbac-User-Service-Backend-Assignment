package com.example.rbac_user_management_service.Service;

import com.example.rbac_user_management_service.dto.AuthRequest;
import com.example.rbac_user_management_service.dto.AuthResponse;
import com.example.rbac_user_management_service.Entity.Role;
import com.example.rbac_user_management_service.Entity.User;
import com.example.rbac_user_management_service.Repository.UserRepository;
import com.example.rbac_user_management_service.Security.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;
@Slf4j
@Service
public class AuthService {
    private UserRepository userRepository;
    private UserService userService;
    private PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthService(UserRepository userRepository,PasswordEncoder passwordEncoder, UserService userService,JwtUtil jwtUtil){
        this.userRepository=userRepository;
        this.passwordEncoder=passwordEncoder;
        this.userService=userService;
        this.jwtUtil=jwtUtil;
    }
    public AuthResponse login(AuthRequest authRequest){
        // 🔵 Audit Log: Login attempt
        log.info("AUDIT: Login attempt for email={}", authRequest.getEmail());
        User user=userRepository.findByEmail(authRequest.getEmail()).orElseThrow(()->{
            // 🔴 Audit Log: No user found
            log.warn("AUDIT: Login failed → User not found, email={}", authRequest.getEmail());
            return new RuntimeException("Invalid email or password");
        });

        if(!passwordEncoder.matches(authRequest.getPassword(),user.getPassword())){
            // 🔴 Audit Log: Incorrect password
            log.warn("AUDIT: Login failed → Incorrect password for email={}", authRequest.getEmail());
            throw new RuntimeException("Invalid email or password");
        }
        userService.updateLastLogin(user);

        // 🔵 Audit Log: Successful login
        log.info("AUDIT: Login successful for email={}", user.getEmail());
        String token=jwtUtil.generateToken(
                user.getId(),
                user.getEmail(),
                user.getRoles().stream().map(r->r.getName()).collect(Collectors.toSet())
        );
        return new AuthResponse(
                token,
                user.getId(),
                user.getEmail(),
                user.getRoles().stream().map(Role::getName).collect(Collectors.toSet())
        );
    }
}
