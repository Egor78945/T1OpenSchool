package com.example.transaction_service.service.client.authentication;

import com.example.transaction_service.model.client.entity.Client;
import com.example.transaction_service.service.common.authentication.AbstractAuthenticationService;

import java.util.UUID;

/**
 * Абстрактная реализация абстрактного сервиса по аутентификации {@link AbstractAuthenticationService} для аутентификации клиентов {@link Client}
 */
public abstract class AbstractClientAuthenticationService <L extends Client,R extends Client> extends AbstractAuthenticationService <L,R> {
    @Override
    public abstract String login(L loginModel);

    @Override
    public abstract String registration(R registrationModel);
}
