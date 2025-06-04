package com.example.transaction_manager_service.environment.transaction;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class TransactionEnvironment {
    private final int TRANSACTION_REQUEST_MAX_COUNT;
    private final int TRANSACTION_REQUEST_PER_MINUTE;

    public TransactionEnvironment(@Value("${transaction.request.max-count}") int TRANSACTION_REQUEST_MAX_COUNT, @Value("${transaction.request.per-minute}") int TRANSACTION_REQUEST_PER_MINUTE) {
        this.TRANSACTION_REQUEST_MAX_COUNT = TRANSACTION_REQUEST_MAX_COUNT;
        this.TRANSACTION_REQUEST_PER_MINUTE = TRANSACTION_REQUEST_PER_MINUTE;
    }

    public int getTRANSACTION_REQUEST_MAX_COUNT() {
        return TRANSACTION_REQUEST_MAX_COUNT;
    }

    public int getTRANSACTION_REQUEST_PER_MINUTE() {
        return TRANSACTION_REQUEST_PER_MINUTE;
    }
}
