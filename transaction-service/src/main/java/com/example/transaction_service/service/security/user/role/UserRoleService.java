package com.example.transaction_service.service.security.user.role;

import com.example.transaction_service.model.user.role.entity.UserRole;

import java.util.List;

public interface UserRoleService<T extends UserRole> {
    void save(T userRole);
    List<T> getAllByUserId(long userId);
    List<T> getAllByUserEmail(String email);
}
