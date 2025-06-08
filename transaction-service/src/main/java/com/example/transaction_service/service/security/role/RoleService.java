package com.example.transaction_service.service.security.role;

import com.example.transaction_service.model.role.entity.Role;

public interface RoleService<R extends Role> {
    R getById(long id);
}
