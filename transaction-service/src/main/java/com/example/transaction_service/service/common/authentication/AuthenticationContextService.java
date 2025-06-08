package com.example.transaction_service.service.common.authentication;

public interface AuthenticationContextService<U> {
    U getCurrentAuthentication();
}
