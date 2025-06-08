package com.example.transaction_service.environment.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class WebClientEnvironment {
    private final String TRANSACTION_MANAGER_SERVICE_HOST;
    private final int TRANSACTION_MANAGER_SERVICE_PORT_INNER;

    public WebClientEnvironment(@Value("${client.transaction-manager.host}") String TRANSACTION_MANAGER_SERVICE_HOST, @Value("${client.transaction-manager.port}") int TRANSACTION_MANAGER_SERVICE_PORT_INNER) {
        this.TRANSACTION_MANAGER_SERVICE_HOST = TRANSACTION_MANAGER_SERVICE_HOST;
        this.TRANSACTION_MANAGER_SERVICE_PORT_INNER = TRANSACTION_MANAGER_SERVICE_PORT_INNER;
    }

    public String getTRANSACTION_MANAGER_SERVICE_HOST() {
        return TRANSACTION_MANAGER_SERVICE_HOST;
    }

    public int getTRANSACTION_MANAGER_SERVICE_PORT_INNER() {
        return TRANSACTION_MANAGER_SERVICE_PORT_INNER;
    }
}
