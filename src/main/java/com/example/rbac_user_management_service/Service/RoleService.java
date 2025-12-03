package com.example.rbac_user_management_service.Service;

import com.example.rbac_user_management_service.Entity.Role;
import com.example.rbac_user_management_service.Repository.RoleRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RoleService {
    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository){
        this.roleRepository=roleRepository;
    }
    public Role createRole(String roleName){
        return roleRepository.findByName(roleName)
                .orElseGet(()->roleRepository.save(Role.builder().name(roleName).build()));
    }
    public Optional<Role> findByName(String roleName){
        return roleRepository.findByName(roleName);

    }

}
