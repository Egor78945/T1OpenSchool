package com.example.blocking_service.service.client.status.implementation;

import com.example.blocking_service.exception.NotFoundException;
import com.example.blocking_service.model.client.status.entity.ClientStatus;
import com.example.blocking_service.repository.ClientStatusRepository;
import com.example.blocking_service.service.client.status.ClientStatusService;
import org.springframework.stereotype.Service;

@Service
public class ClientStatusServiceManager implements ClientStatusService<ClientStatus> {
    private final ClientStatusRepository clientStatusRepository;

    public ClientStatusServiceManager(ClientStatusRepository clientStatusRepository) {
        this.clientStatusRepository = clientStatusRepository;
    }

    @Override
    public ClientStatus getById(long id) {
        return clientStatusRepository.findById(id).orElseThrow(() -> new NotFoundException(String.format("client status is not found by id\nid : %s", id)));
    }
}
