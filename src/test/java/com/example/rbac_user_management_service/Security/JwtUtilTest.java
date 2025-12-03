package com.example.rbac_user_management_service.Security;

import io.jsonwebtoken.Claims;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

class JwtUtilTest {

    private JwtUtil jwtUtil;

    @BeforeEach
    void setup() {
        // Use a long 32+ char key for HMAC-SHA
        jwtUtil = new JwtUtil("THIS_IS_A_TEST_SECRET_KEY_1234567890", 3600000);
    }

    @Test
    void tokenShouldContainCorrectEmail() {
        String token = jwtUtil.generateToken(
                1L,
                "abc@gmail.com",
                Set.of("ROLE_USER")
        );

        Claims claims = jwtUtil.getClaims(token);

        assertEquals("abc@gmail.com", claims.getSubject());
    }
}

