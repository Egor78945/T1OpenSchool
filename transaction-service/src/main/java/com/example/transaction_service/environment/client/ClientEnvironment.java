package com.example.transaction_service.environment.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ClientEnvironment {
    private int CLIENT_BLOCKED_MAX_SELECT_COUNT;

    public ClientEnvironment(@Value("${client.blocked.max-select-count}") int CLIENT_BLOCKED_MAX_SELECT_COUNT) {
        this.CLIENT_BLOCKED_MAX_SELECT_COUNT = CLIENT_BLOCKED_MAX_SELECT_COUNT;
    }

    public int getCLIENT_BLOCKED_MAX_SELECT_COUNT() {
        return CLIENT_BLOCKED_MAX_SELECT_COUNT;
    }
}
