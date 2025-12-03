package com.example.rbac_user_management_service.Service;

import com.example.rbac_user_management_service.Entity.Role;
import com.example.rbac_user_management_service.Entity.User;
import com.example.rbac_user_management_service.Repository.UserRepository;
import com.example.rbac_user_management_service.dto.UserDto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class UserServiceTest {

    private UserRepository userRepository;
    private RoleService roleService;
    private PasswordEncoder passwordEncoder;
    private ModelMapper modelMapper;

    private UserService userService;

    @BeforeEach
    void setup() {
        userRepository = mock(UserRepository.class);
        roleService = mock(RoleService.class);
        passwordEncoder = mock(PasswordEncoder.class);
        modelMapper = mock(ModelMapper.class);

        userService = new UserService(userRepository, roleService, passwordEncoder, modelMapper);
    }

    @Test
    void getUserByIdSuccess() {

        User user = new User();
        user.setId(1L);
        user.setEmail("john@test.com");
        user.setUsername("john");

        UserDto dto = new UserDto(
                1L, "john", "john@test.com", Set.of("ROLE_USER"), Instant.now()
        );

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(modelMapper.map(user, UserDto.class)).thenReturn(dto);

        UserDto result = userService.getUserById(1L);

        assertEquals("john@test.com", result.getEmail());
        assertEquals("john", result.getUsername());
    }

    @Test
    void getUserById_NotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> userService.getUserById(1L));
    }

    @Test
    void updateLastLoginShouldUpdateTimestamp() {
        User user = new User();
        user.setId(1L);

        userService.updateLastLogin(user);

        assertNotNull(user.getLastLoginAt());
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void getAllUsersShouldReturnDtoList() {

        User user = new User();
        user.setId(1L);
        user.setEmail("john@test.com");
        user.setUsername("john");

        UserDto dto = new UserDto(
                1L, "john", "john@test.com", Set.of("ROLE_USER"), Instant.now()
        );

        when(userRepository.findAll()).thenReturn(List.of(user));
        when(modelMapper.map(user, UserDto.class)).thenReturn(dto);

        List<UserDto> result = userService.getAllUsers();

        assertEquals(1, result.size());
        assertEquals("john@test.com", result.get(0).getEmail());
    }
}

