package com.example.rbac_user_management_service.Controller;

import com.example.rbac_user_management_service.dto.AuthRequest;
import com.example.rbac_user_management_service.dto.AuthResponse;
import com.example.rbac_user_management_service.dto.RegisterRequest;
import com.example.rbac_user_management_service.Entity.User;
import com.example.rbac_user_management_service.Service.AuthService;
import com.example.rbac_user_management_service.Service.KafkaEventProducer;
import com.example.rbac_user_management_service.Service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class AuthController {
    private final UserService userService;
    private final AuthService authService;
    private final KafkaEventProducer producer;

    public AuthController(UserService userService, AuthService authService, KafkaEventProducer producer) {
        this.userService = userService;
        this.authService = authService;
        this.producer = producer;
    }


    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest req) {
        User user = userService.registerUser(req.getUsername(), req.getEmail(), req.getPassword());
        producer.publishEvent("user-events", Map.of("type", "REGISTER", "email", user.getEmail(), "timestamp", System.currentTimeMillis()));
        return ResponseEntity.ok(Map.of("message", "User Registered Successfully", "userId", user.getId()));
    }


    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody AuthRequest req) {
        AuthResponse res = authService.login(req);

        // send Kafka event
        producer.publishEvent("user-events", Map.of("type", "LOGIN", "email", res.getEmail(), "timestamp", System.currentTimeMillis()));

        return ResponseEntity.ok(res);
    }
}

