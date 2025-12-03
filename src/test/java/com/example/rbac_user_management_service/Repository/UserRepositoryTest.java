package com.example.rbac_user_management_service.Repository;

import com.example.rbac_user_management_service.Entity.Role;
import com.example.rbac_user_management_service.Entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void testFindByEmail() {

        Role role = new Role();
        role.setName("ROLE_USER");

        User user = new User();
        user.setUsername("john");
        user.setEmail("john@test.com");
        user.setPassword("12345");
        user.setRoles(Set.of(role)); // Role will be cascaded

        userRepository.save(user);

        Optional<User> result = userRepository.findByEmail("john@test.com");

        assertTrue(result.isPresent());
        assertEquals("john@test.com", result.get().getEmail());
    }
}


