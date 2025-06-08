package com.example.transaction_service.service.security.user.role.implementation;

import com.example.transaction_service.model.user.role.entity.UserRole;
import com.example.transaction_service.repository.UserRoleRepository;
import com.example.transaction_service.service.security.user.role.UserRoleService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserRoleServiceManager implements UserRoleService<UserRole> {
    private final UserRoleRepository userRoleRepository;

    public UserRoleServiceManager(UserRoleRepository userRoleRepository) {
        this.userRoleRepository = userRoleRepository;
    }

    @Override
    public void save(UserRole userRole) {
        userRoleRepository.save(userRole);
    }

    @Override
    public List<UserRole> getAllByUserId(long userId) {
        return userRoleRepository.findAllByUserId(userId);
    }

    @Override
    public List<UserRole> getAllByUserEmail(String email) {
        return userRoleRepository.findAllByUserEmail(email);
    }
}
