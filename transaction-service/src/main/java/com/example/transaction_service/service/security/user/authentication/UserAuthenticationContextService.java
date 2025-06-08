package com.example.transaction_service.service.security.user.authentication;

import com.example.transaction_service.model.user.entity.User;
import com.example.transaction_service.service.common.aop.annotation.Cached;
import com.example.transaction_service.service.common.authentication.AuthenticationContextService;
import com.example.transaction_service.service.security.user.UserService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class UserAuthenticationContextService implements AuthenticationContextService<User> {
    private final UserService<User> userService;

    public UserAuthenticationContextService(@Qualifier("userServiceManager") UserService<User> userService) {
        this.userService = userService;
    }

    @Override
    @Cached
    public User getCurrentAuthentication() {
        return userService.getByEmail(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
    }
}
