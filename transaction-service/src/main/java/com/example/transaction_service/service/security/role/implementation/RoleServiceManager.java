package com.example.transaction_service.service.security.role.implementation;

import com.example.transaction_service.exception.NotFoundException;
import com.example.transaction_service.model.role.entity.Role;
import com.example.transaction_service.repository.RoleRepository;
import com.example.transaction_service.service.security.role.RoleService;
import org.springframework.stereotype.Repository;

@Repository
public class RoleServiceManager implements RoleService<Role> {
    private final RoleRepository roleRepository;

    public RoleServiceManager(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public Role getById(long id) {
        return roleRepository.findById(id).orElseThrow(() -> new NotFoundException(String.format("role is not found by id\nid : %s", id)));
    }
}
