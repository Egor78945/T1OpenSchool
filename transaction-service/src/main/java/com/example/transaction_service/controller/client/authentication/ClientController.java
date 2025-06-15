package com.example.transaction_service.controller.client.authentication;

import com.example.transaction_service.controller.common.advice.handler.CommonControllerExceptionHandler;
import com.example.transaction_service.model.client.entity.Client;
import com.example.transaction_service.model.user.entity.User;
import com.example.transaction_service.service.client.AbstractClientService;
import com.example.transaction_service.service.common.authentication.AuthenticationContextService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Контроллер, принимающий запросы, связанные с аутентификацией клиентов
 */
@RestController
@RequestMapping("/api/v1/client")
@CommonControllerExceptionHandler
public class ClientController {
    private final AbstractClientService<Client> clientService;
    private final AuthenticationContextService<User> userAuthenticationContextService;

    public ClientController(@Qualifier("clientServiceManager") AbstractClientService<Client> clientService, @Qualifier("userAuthenticationContextServiceManager") AuthenticationContextService<User> userAuthenticationContextService) {
        this.clientService = clientService;
        this.userAuthenticationContextService = userAuthenticationContextService;
    }

    @GetMapping
    public ResponseEntity<Client> getByUserEmail() {
        return ResponseEntity.ok(clientService.getByUserEmail(userAuthenticationContextService.getCurrentAuthentication().getEmail()));
    }
}
