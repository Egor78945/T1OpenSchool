package com.example.transaction_service.controller.user.authentication;

import com.example.transaction_service.controller.common.advice.handler.CommonControllerExceptionHandler;
import com.example.transaction_service.model.user.dto.UserDetailsDTO;
import com.example.transaction_service.service.common.authentication.AbstractAuthenticationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user/authentication")
@CommonControllerExceptionHandler
public class UserAuthenticationController {
    private final AbstractAuthenticationService<UserDetailsDTO> authenticationService;

    public UserAuthenticationController(@Qualifier("userAuthenticationService") AbstractAuthenticationService<UserDetailsDTO> authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/registration")
    public void registration(@RequestBody @Valid UserDetailsDTO userDetailsDTO) {
        authenticationService.registration(userDetailsDTO);
    }

    @GetMapping("/login")
    public ResponseEntity<String> login(@RequestParam("email") String email, @RequestParam("password") String password) {
        return ResponseEntity.ok(authenticationService.login(email, password));
    }
}
