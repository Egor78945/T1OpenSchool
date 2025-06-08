package com.example.transaction_service.controller.client.authentication;

import com.example.transaction_service.controller.common.advice.handler.CommonControllerExceptionHandler;
import com.example.transaction_service.model.client.dto.ClientDTO;
import com.example.transaction_service.model.client.entity.Client;
import com.example.transaction_service.service.client.authentication.AbstractClientAuthenticationService;
import com.example.transaction_service.service.client.mapper.ClientMapper;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Контроллер, принимающий запросы, связанные с аутентификацией клиентов
 */
@RestController
@RequestMapping("/api/v1/authentication")
@CommonControllerExceptionHandler
public class ClientAuthenticationController {
    private final AbstractClientAuthenticationService<Client> clientAuthenticationService;

    public ClientAuthenticationController(@Qualifier("clientAuthenticationServiceManager") AbstractClientAuthenticationService<Client> clientAuthenticationService) {
        this.clientAuthenticationService = clientAuthenticationService;
    }

    /**
     * Метод, выполняющий регистрацию нового клиента
     * @param clientDTO Регистрационные данные нового клиента {@link Client}
     * @return <b>UUID</b> идентификатор новосозданного клиента
     */
    @PostMapping("/registration")
    public ResponseEntity<String> registration(@RequestBody @Valid ClientDTO clientDTO) {
        return ResponseEntity.ok(clientAuthenticationService.registration(ClientMapper.mapTo(clientDTO)).getClient_id().toString());
    }
}
