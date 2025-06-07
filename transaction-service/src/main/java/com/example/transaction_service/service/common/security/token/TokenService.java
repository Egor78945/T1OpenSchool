package com.example.transaction_service.service.common.security.token;

import java.util.List;

public interface TokenService <T, C> {
    T generateToken(C credential);
    String extractUsernameFromToken(T token);
    boolean isTokenValid(T token);
    List<String> extractAuthoritiesFromToken(T token);
}
