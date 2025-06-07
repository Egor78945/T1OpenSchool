package com.example.transaction_service.service.security.user.credential.implementation;

import com.example.transaction_service.model.user.dto.UserCredential;
import com.example.transaction_service.repository.UserRepository;
import com.example.transaction_service.service.security.user.credential.UserCredentialService;
import org.springframework.stereotype.Service;

@Service
public class UserCredentialServiceManager implements UserCredentialService<UserCredential> {
    private final UserRepository userRepository;

    public UserCredentialServiceManager(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public boolean existsByUserEmail(String email) {
        return userRepository.existsUserByEmail(email);
    }
}
