package com.example.transaction_service.service.client.authentication.implementation;

import com.example.transaction_service.model.client.entity.Client;
import com.example.transaction_service.service.client.AbstractClientService;
import com.example.transaction_service.service.client.authentication.AbstractClientAuthenticationService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * Реализация абстрактного сервиса по аутентификации клиентов {@link Client}
 */
@Service
public class ClientAuthenticationServiceManager extends AbstractClientAuthenticationService<Client> {
    private final AbstractClientService<Client> clientService;

    public ClientAuthenticationServiceManager(@Qualifier("clientServiceManager") AbstractClientService<Client> clientService) {
        this.clientService = clientService;
    }

    @Override
    public String login(String username, String password) {
        //TODO
        return null;
    }

    @Override
    public Client registration(Client registrationModel) {
        return clientService.save(registrationModel);
    }
}
