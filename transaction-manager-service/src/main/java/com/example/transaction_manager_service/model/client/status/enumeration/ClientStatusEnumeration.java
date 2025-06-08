package com.example.transaction_manager_service.model.client.status.enumeration;

public enum ClientStatusEnumeration {
    ACTIVE(1), BLOCKED(2);
    private final long id;

    ClientStatusEnumeration(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }
}
