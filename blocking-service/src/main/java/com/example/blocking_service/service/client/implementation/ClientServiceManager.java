package com.example.blocking_service.service.client.implementation;

import com.example.blocking_service.exception.NotFoundException;
import com.example.blocking_service.exception.ProcessingException;
import com.example.blocking_service.model.client.entity.Client;
import com.example.blocking_service.repository.ClientRepository;
import com.example.blocking_service.service.client.ClientService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ClientServiceManager implements ClientService<Client> {
    private final ClientRepository clientRepository;

    public ClientServiceManager(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    public Client update(Client client) {
        if(clientRepository.existsById(client.getId()) && clientRepository.existsClientByClientId(client.getClient_id())){
            return clientRepository.save(client);
        }
        throw new ProcessingException(String.format("client can not be updated\nClient : %s", client));
    }

    @Override
    public Client getById(long id) {
        return clientRepository.findById(id).orElseThrow(() -> new NotFoundException(String.format("client is not found by id\nid : %s", id)));
    }

    @Override
    public Client getByNaturalId(UUID clientId) {
        return clientRepository.findClientByClient_id(clientId).orElseThrow(() -> new NotFoundException(String.format("client is not found by client id\nclient id : %s", clientId)));
    }

    @Override
    @Transactional
    public void changeStatusByNaturalIdAndStatusId(UUID naturalId, long statusId){
        clientRepository.updateClientStatusByClient_idAndClientStatus(naturalId, statusId);
    }

    @Override
    public boolean existsByNaturalId(UUID clientId) {
        return clientRepository.existsClientByClientId(clientId);
    }

    @Override
    public boolean existsById(long id) {
        return clientRepository.existsById(id);
    }
}
