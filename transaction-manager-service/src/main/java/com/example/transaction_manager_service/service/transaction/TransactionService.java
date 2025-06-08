package com.example.transaction_manager_service.service.transaction;

import com.example.transaction_manager_service.model.transaction.entity.Transaction;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

public interface TransactionService<T extends Transaction> {
    T save(T transaction);
    T getById(long id);
    T getByTransactionId(UUID uuid);
    List<T> getBySenderIdAfter(long id, Timestamp afterTime);
}
