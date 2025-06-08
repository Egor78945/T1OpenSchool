package com.example.transaction_service.service.client.authentication;

import com.example.transaction_service.model.client.entity.Client;
import com.example.transaction_service.service.common.authentication.AbstractAuthenticationService;

import java.util.UUID;

/**
 * Абстрактная реализация абстрактного сервиса по аутентификации {@link AbstractAuthenticationService} для аутентификации клиентов {@link Client}
 */
public abstract class AbstractClientAuthenticationService <R extends Client> extends AbstractAuthenticationService <R> {
    @Override
    public abstract String login(String username, String password);

    @Override
    public abstract R registration(R registrationModel);
}
