package com.example.transaction_service.service.client.authentication.implementation;

import com.example.transaction_service.exception.AuthenticationException;
import com.example.transaction_service.model.client.entity.Client;
import com.example.transaction_service.repository.ClientRepository;
import com.example.transaction_service.service.client.AbstractClientService;
import com.example.transaction_service.service.client.implementation.ClientServiceManager;
import com.example.transaction_service.service.common.aop.annotation.LogDatasourceError;
import com.example.transaction_service.service.client.authentication.AbstractClientAuthenticationService;
import com.example.transaction_service.service.common.aop.annotation.Metric;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

/**
 * Реализация абстрактного сервиса по аутентификации клиентов {@link Client}
 */
@Service
public class ClientAuthenticationServiceManager extends AbstractClientAuthenticationService<Client, Client> {
    private final AbstractClientService<Client> clientService;

    public ClientAuthenticationServiceManager(@Qualifier("clientServiceManager") AbstractClientService<Client> clientService) {
        this.clientService = clientService;
    }

    @Override
    public String login(Client loginModel) {
        //TODO
        return null;
    }

    @Override
    public String registration(Client registrationModel) {
        if (registrationModel.getId() == null) {
            return clientService.save(registrationModel).toString();
        } else {
            throw new AuthenticationException(String.format("client can not be registered successfully\nClient : %s", registrationModel));
        }
    }
}
