package com.example.rbac_user_management_service.Controller;

import com.example.rbac_user_management_service.Service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
@RestController
@RequestMapping("/api/admin")
public class AdminController {
    private final UserService userService;

    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping("/stats")
    public ResponseEntity<?> getStats() {

        long totalUsers = userService.countUsers();

        return ResponseEntity.ok(Map.of(
                "totalUsers", totalUsers,
                "lastUpdated", System.currentTimeMillis()
        ));
    }

}
