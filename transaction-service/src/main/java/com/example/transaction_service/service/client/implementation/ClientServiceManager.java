package com.example.transaction_service.service.client.implementation;

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
    public UUID save(Client client) {
        client.setClient_id(UUID.randomUUID());
        for(int i = 0; i < 10; i++){
            if(existsByClientId(client.getClient_id())){
                client.setClient_id(UUID.randomUUID());
            } else {
                return clientRepository.save(client).getClient_id();
            }
        }
        throw new ProcessingException("uuid is not generated");
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
    public boolean existsByClientId(UUID clientId) {
        return clientRepository.existsClientByClientId(clientId);
    }
}
