package com.example.transaction_service.environment.token;

import com.example.transaction_service.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class TokenEnvironment {
    private final long TOKEN_LIFETIME;

    public TokenEnvironment(@Value("${token.lifetime}") long TOKEN_LIFETIME) {
        this.TOKEN_LIFETIME = TOKEN_LIFETIME;
    }

    public long getTOKEN_LIFETIME() {
        return TOKEN_LIFETIME;
    }

    public String getTOKEN_SECRET() {
        return Optional.ofNullable(System.getenv("TOKEN_SECRET")).orElseThrow(() -> new NotFoundException("token is not found"));
    }
}
