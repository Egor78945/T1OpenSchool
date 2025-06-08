package com.example.transaction_service.service.client.implementation;

import com.example.transaction_service.exception.AuthenticationException;
import com.example.transaction_service.exception.NotFoundException;
import com.example.transaction_service.exception.ProcessingException;
import com.example.transaction_service.model.client.entity.Client;
import com.example.transaction_service.repository.ClientRepository;
import com.example.transaction_service.service.client.AbstractClientService;
import com.example.transaction_service.service.common.aop.annotation.LogDatasourceError;
import com.example.transaction_service.service.common.aop.annotation.Metric;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * Реализация абстрактного сервиса по работе с клиентами {@link }
 */
@Service
public class ClientServiceManager extends AbstractClientService<Client> {
    public ClientServiceManager(ClientRepository clientRepository) {
        super(clientRepository);
    }

    @Override
    @LogDatasourceError
    @Metric
    public Client save(Client client) {
        if (client.getId() == null && client.getClient_id() == null) {
            client.setClient_id(buildUUID());
            return clientRepository.save(client);
        } else {
            throw new AuthenticationException(String.format("client can not be saved successfully\nClient : %s", client));
        }
    }

    @Override
    public Client getById(long id) {
        return clientRepository.findById(id).orElseThrow(() -> new NotFoundException(String.format("client by id is not found\nid : %s", id)));
    }

    @Override
    public Client getByClientId(UUID clientId) {
        return clientRepository.findByClientId(clientId).orElseThrow(() -> new NotFoundException(String.format("client by client id is not found\nclient id : %s", clientId)));
    }

    @Override
    public Client getByUserId(long userId) {
        return clientRepository.findByUserId(userId).orElseThrow(() -> new NotFoundException(String.format("client by user id is not found\nuser id : %s", userId)));
    }

    @Override
    public Client getByUserEmail(String email) {
        return clientRepository.findByUserEmail(email).orElseThrow(() -> new NotFoundException(String.format("client by user email is not found\nuser email : %s", email)));
    }

    @Override
    public boolean existsByClientId(UUID clientId) {
        return clientRepository.existsClientByClientId(clientId);
    }
}
