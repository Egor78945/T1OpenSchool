package com.example.blocking_service.service.client;

import com.example.blocking_service.model.client.entity.Client;
import com.example.blocking_service.service.common.EntityService;

import java.util.UUID;

public interface ClientService <C extends Client> extends EntityService<C> {
    @Override
    C update(Client client);

    @Override
    C getById(long id);

    @Override
    C getByNaturalId(UUID naturalId);

    void changeStatusByNaturalIdAndStatusId(UUID naturalId, long statusId);

    @Override
    boolean existsByNaturalId(UUID naturalId);

    @Override
    boolean existsById(long id);
}
