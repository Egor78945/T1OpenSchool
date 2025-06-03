package com.example.transaction_manager_service.model.transaction.type.enumeration;

import java.util.Map;
import java.util.Optional;

/**
 * Перечисление типов транзакций по клиентским аккаунтам
 */
public enum TransactionTypeEnumeration {
    INSERT(1), TRANSFER(2);
    private final long id;
    private final static Map<Long, TransactionTypeEnumeration> transactionTypeEnumerationById;
    private final static Map<String, TransactionTypeEnumeration> transactionTypeEnumerationByName;

    TransactionTypeEnumeration(long id) {
        this.id = id;
    }

    static {
        transactionTypeEnumerationById = Map.of(INSERT.getId(), INSERT, TRANSFER.getId(), TRANSFER);
        transactionTypeEnumerationByName = Map.of(INSERT.name(), INSERT, TRANSFER.name(), TRANSFER);
    }

    public long getId() {
        return id;
    }

    public static Optional<TransactionTypeEnumeration> getById(long id) {
        return Optional.ofNullable(transactionTypeEnumerationById.get(id));
    }

    public static Optional<TransactionTypeEnumeration> getByName(String name) {
        return Optional.ofNullable(transactionTypeEnumerationByName.get(name));
    }
}
