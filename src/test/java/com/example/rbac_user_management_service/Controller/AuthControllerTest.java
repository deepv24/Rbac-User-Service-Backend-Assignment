package com.example.rbac_user_management_service.Controller;

import com.example.rbac_user_management_service.Service.AuthService;
import com.example.rbac_user_management_service.Service.KafkaEventProducer;
import com.example.rbac_user_management_service.Service.UserService;
import com.example.rbac_user_management_service.dto.AuthRequest;
import com.example.rbac_user_management_service.dto.AuthResponse;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AuthController.class)
@AutoConfigureMockMvc(addFilters = false)   // ⭐ DISABLE JWT + CSRF + SECURITY FILTERS
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthService authService;  // ⭐ REQUIRED

    @MockBean
    private UserService userService;  // ⭐ REQUIRED

    @MockBean
    private KafkaEventProducer kafkaEventProducer; // ⭐ REQUIRED

    @Test
    void loginShouldReturn200() throws Exception {

        AuthResponse mockResponse = new AuthResponse(
                "token-123",
                1L,
                "john@gmail.com",
                Set.of("ROLE_USER")
        );

        when(authService.login(any(AuthRequest.class))).thenReturn(mockResponse);

        mockMvc.perform(post("/api/users/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                    {
                      "email":"john@gmail.com",
                      "password":"pass123"
                    }
                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value("token-123"))
                .andExpect(jsonPath("$.userId").value(1))
                .andExpect(jsonPath("$.email").value("john@gmail.com"));
    }
}

