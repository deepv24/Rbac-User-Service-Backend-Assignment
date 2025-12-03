package com.example.rbac_user_management_service.Service;

import com.example.rbac_user_management_service.dto.AuthRequest;
import com.example.rbac_user_management_service.dto.AuthResponse;
import com.example.rbac_user_management_service.Entity.Role;
import com.example.rbac_user_management_service.Entity.User;
import com.example.rbac_user_management_service.Repository.UserRepository;
import com.example.rbac_user_management_service.Security.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.*;

import org.springframework.security.crypto.password.PasswordEncoder;

class AuthServiceTest {

    private UserRepository userRepository = mock(UserRepository.class);
    private PasswordEncoder passwordEncoder = mock(PasswordEncoder.class);
    private UserService userService = mock(UserService.class);
    private JwtUtil jwtUtil = mock(JwtUtil.class);

    private AuthService authService;

    @BeforeEach
    void setup() {
        authService = new AuthService(userRepository, passwordEncoder, userService, jwtUtil);
    }

    @Test
    void loginSuccess() {

        // Mock User object
        User user = new User();
        user.setId(1L);
        user.setEmail("john@test.com");
        user.setPassword("encodedPass");

        Role role = new Role();
        role.setName("ROLE_USER");

        user.setRoles(Set.of(role));

        AuthRequest req = new AuthRequest("john@test.com", "password");

        when(userRepository.findByEmail("john@test.com")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("password", "encodedPass")).thenReturn(true);
        when(jwtUtil.generateToken(anyLong(), anyString(), anySet())).thenReturn("mock-jwt-token");

        AuthResponse resp = authService.login(req);

        assertEquals("mock-jwt-token", resp.getToken());
        assertEquals(1L, resp.getUserId());
        assertEquals("john@test.com", resp.getEmail());
    }

    @Test
    void loginFails_WrongPassword() {

        User user = new User();
        user.setPassword("encodedPass");

        when(userRepository.findByEmail("john@test.com"))
                .thenReturn(Optional.of(user));

        when(passwordEncoder.matches(any(), any())).thenReturn(false);

        AuthRequest req = new AuthRequest("john@test.com", "badpass");

        assertThrows(RuntimeException.class, () -> authService.login(req));
    }

    @Test
    void loginFails_UserNotFound() {

        when(userRepository.findByEmail("john@test.com"))
                .thenReturn(Optional.empty());

        AuthRequest req = new AuthRequest("john@test.com", "pass123");

        assertThrows(RuntimeException.class, () -> authService.login(req));
    }
}

