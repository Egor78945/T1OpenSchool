package com.example.transaction_manager_service.model.transaction.status.enumeration;

import java.util.Map;
import java.util.Optional;

public enum TransactionStatusEnumeration {
    ACCEPTED(1), REJECTED(2), BLOCKED(3), CANCELLED(4), REQUESTED(5);
    private final long id;
    private static final Map<Long, TransactionStatusEnumeration> transactionStatusEnumerationById;

    TransactionStatusEnumeration(long id) {
        this.id = id;
    }

    static {
        transactionStatusEnumerationById = Map.of(1L, ACCEPTED, 2L, REJECTED, 3L, BLOCKED, 4L, CANCELLED, 5L, REQUESTED);
    }

    public long getId() {
        return id;
    }

    public static Optional<TransactionStatusEnumeration> getById(long id){
        return Optional.ofNullable(transactionStatusEnumerationById.get(id));
    }
}
