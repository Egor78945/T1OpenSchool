package com.example.transaction_service.environment.web;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class WebClientEnvironment {
    private final String TRANSACTION_MANAGER_SERVICE_HOST;
    private final String BLOCKING_SERVICE_HOST;
    private final int TRANSACTION_MANAGER_SERVICE_PORT_INNER;
    private final int BLOCKING_SERVICE_PORT_INNER;

    public WebClientEnvironment(@Value("${web.client.transaction-manager.host}") String TRANSACTION_MANAGER_SERVICE_HOST, @Value("${web.client.transaction-manager.port}") int TRANSACTION_MANAGER_SERVICE_PORT_INNER, @Value("${web.client.blocking-service.host}") String BLOCKING_SERVICE_HOST, @Value("${web.client.blocking-service.port}") int BLOCKING_SERVICE_PORT_INNER) {
        this.TRANSACTION_MANAGER_SERVICE_HOST = TRANSACTION_MANAGER_SERVICE_HOST;
        this.BLOCKING_SERVICE_HOST = BLOCKING_SERVICE_HOST;
        this.TRANSACTION_MANAGER_SERVICE_PORT_INNER = TRANSACTION_MANAGER_SERVICE_PORT_INNER;
        this.BLOCKING_SERVICE_PORT_INNER = BLOCKING_SERVICE_PORT_INNER;
    }

    public String getTRANSACTION_MANAGER_SERVICE_HOST() {
        return TRANSACTION_MANAGER_SERVICE_HOST;
    }

    public int getTRANSACTION_MANAGER_SERVICE_PORT_INNER() {
        return TRANSACTION_MANAGER_SERVICE_PORT_INNER;
    }

    public String getBLOCKING_SERVICE_HOST() {
        return BLOCKING_SERVICE_HOST;
    }

    public int getBLOCKING_SERVICE_PORT_INNER() {
        return BLOCKING_SERVICE_PORT_INNER;
    }
}
