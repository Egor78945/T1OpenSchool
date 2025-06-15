package com.example.blocking_service.service.client.status;

import com.example.blocking_service.model.client.status.entity.ClientStatus;

public interface ClientStatusService<CS extends ClientStatus> {
    CS getById(long id);
}
