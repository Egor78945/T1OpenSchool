package com.example.transaction_service.service.security.user;

import com.example.transaction_service.model.user.entity.User;

public interface UserService<T extends User> {
    T save(User user);
    T update(User user);
    T getById(long id);
    T getByEmail(String email);
    boolean existsByEmail(String email);
}
