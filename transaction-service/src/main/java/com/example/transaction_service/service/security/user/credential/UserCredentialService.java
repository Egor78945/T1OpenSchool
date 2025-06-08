package com.example.transaction_service.service.security.user.credential;

import com.example.transaction_service.model.user.dto.UserCredentialDTO;

public interface UserCredentialService<T extends UserCredentialDTO> {
    boolean existsByUserEmail(String email);
    T getByUserEmail(String email);
}
