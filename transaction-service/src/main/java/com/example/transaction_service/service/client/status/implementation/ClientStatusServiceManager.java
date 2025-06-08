package com.example.transaction_service.service.client.status.implementation;

import com.example.transaction_service.exception.NotFoundException;
import com.example.transaction_service.model.client.status.entity.ClientStatus;
import com.example.transaction_service.repository.ClientStatusRepository;
import com.example.transaction_service.service.client.status.ClientStatusService;
import com.example.transaction_service.service.common.aop.annotation.Cached;
import org.springframework.stereotype.Service;

@Service
public class ClientStatusServiceManager implements ClientStatusService<ClientStatus> {
    private final ClientStatusRepository clientStatusRepository;

    public ClientStatusServiceManager(ClientStatusRepository clientStatusRepository) {
        this.clientStatusRepository = clientStatusRepository;
    }

    @Override
    @Cached
    public ClientStatus getById(long id) {
        return clientStatusRepository.findById(id).orElseThrow(() -> new NotFoundException(String.format("client status is not found by id\nid : %s", id)));
    }
}
