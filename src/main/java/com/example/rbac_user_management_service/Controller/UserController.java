package com.example.rbac_user_management_service.Controller;

import com.example.rbac_user_management_service.dto.UserDto;
import com.example.rbac_user_management_service.Service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // Get Logged in user Profile

    @GetMapping("/me")
    public ResponseEntity<UserDto> getCurrentUser(Authentication auth) {
        String email = auth.getName();
        return ResponseEntity.ok(userService.getUserProfile(email));
    }

    // ADMIN ONLY: Assign role to a user

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PostMapping("/{userId}/roles")
    public ResponseEntity<?> assignRole(
            @PathVariable Long userId,
            @RequestParam String role
    ) {
        userService.assignRole(userId, role);
        return ResponseEntity.ok("Role assigned successfully");
    }
}



