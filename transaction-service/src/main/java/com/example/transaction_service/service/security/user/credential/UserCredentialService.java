package com.example.transaction_service.service.security.user.credential;

import com.example.transaction_service.model.user.dto.UserCredential;

public interface UserCredentialService<T extends UserCredential> {
    boolean existsByUserEmail(String email);
}
