package com.example.transaction_service.service.client.status;

import com.example.transaction_service.model.client.status.entity.ClientStatus;

public interface ClientStatusService <T extends ClientStatus> {
    T getById(long id);
}
