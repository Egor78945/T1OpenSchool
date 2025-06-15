package com.example.blocking_service.service.client.processor.implementation;

import com.example.blocking_service.model.client.entity.Client;
import com.example.blocking_service.model.client.status.enumeration.ClientStatusEnumeration;
import com.example.blocking_service.service.client.ClientService;
import com.example.blocking_service.service.client.processor.ClientProcessorService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientProcessorServiceManager implements ClientProcessorService<Client> {
    private final ClientService<Client> clientService;

    public ClientProcessorServiceManager(@Qualifier("clientServiceManager") ClientService<Client> clientService) {
        this.clientService = clientService;
    }

    @Override
    public void unblock(Client client) {
        if (clientService.existsById(client.getId())) {
            clientService.changeStatusByNaturalIdAndStatusId(client.getClient_id(), ClientStatusEnumeration.ACTIVE.getId());
        }
    }

    @Override
    public void unblock(List<Client> clients) {
        for (Client c : clients) {
            unblock(c);
        }
    }
}
