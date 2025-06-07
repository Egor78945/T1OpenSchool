package com.example.transaction_service.service.common.security.token.implementation;

import com.example.transaction_service.environment.token.TokenEnvironment;
import com.example.transaction_service.model.user.dto.UserCredential;
import com.example.transaction_service.service.common.security.token.TokenService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class JWTService implements TokenService<String, UserCredential> {
    private final TokenEnvironment tokenEnvironment;

    public JWTService(TokenEnvironment tokenEnvironment) {
        this.tokenEnvironment = tokenEnvironment;
    }

    @Override
    public String generateToken(UserCredential credential) {
        return Jwts.builder()
                .claims(Map.of("roles", credential.getUserRole()
                        .stream()
                        .map(r -> r.getRole_id().getName())
                        .toList()))
                .subject(credential.getUser().getEmail())
                .expiration(new Date(System.currentTimeMillis() + tokenEnvironment.getTOKEN_LIFETIME()))
                .signWith(Keys.hmacShaKeyFor(Decoders.BASE64.decode(tokenEnvironment.getTOKEN_SECRET())))
                .compact();
    }

    @Override
    public String extractUsernameFromToken(String token) {
        return extractClaimsFromToken(token).getSubject();
    }

    @Override
    public boolean isTokenValid(String token) {
        try {
            Claims claims = extractClaimsFromToken(token);
            return claims.get("roles", List.class) != null && claims.getSubject() != null;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public List<String> extractAuthoritiesFromToken(String token) {
        return extractClaimsFromToken(token).get("roles", List.class);
    }

    private Claims extractClaimsFromToken(String token) {
        try {
            return Jwts
                    .parser()
                    .verifyWith(Keys.hmacShaKeyFor(Decoders.BASE64.decode(tokenEnvironment.getTOKEN_SECRET())))
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
        } catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }

}
