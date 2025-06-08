package com.example.transaction_service.configuration.security.filter;

import com.example.transaction_service.model.user.dto.UserCredentialDTO;
import com.example.transaction_service.service.common.security.token.TokenService;
import com.example.transaction_service.service.security.user.credential.UserCredentialService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class TokenFilter extends OncePerRequestFilter {
    private final TokenService<String, UserCredentialDTO> tokenService;
    private final UserCredentialService<UserCredentialDTO> userCredentialService;

    public TokenFilter(@Qualifier("JWTService") TokenService<String, UserCredentialDTO> tokenService, @Qualifier("userCredentialServiceManager") UserCredentialService<UserCredentialDTO> userCredentialService) {
        this.tokenService = tokenService;
        this.userCredentialService = userCredentialService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            try {
                if (tokenService.isTokenValid(token) && userCredentialService.existsByUserEmail(tokenService.extractUsernameFromToken(token))) {
                    String email = tokenService.extractUsernameFromToken(token);
                    List<String> authorities = tokenService.extractAuthoritiesFromToken(token);
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            email, null, authorities
                            .stream()
                            .map(SimpleGrantedAuthority::new)
                            .collect(Collectors.toList())
                    );
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                } else {
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "invalid token");
                    return;
                }
            } catch (Exception e) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, e.getMessage());
                return;
            }
        }
        filterChain.doFilter(request, response);
    }
}
