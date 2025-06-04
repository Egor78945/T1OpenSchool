package com.example.transaction_manager_service.service.transaction;

import com.example.transaction_manager_service.model.transaction.entity.Transaction;

public interface TransactionProcessorService<T extends Transaction> {
    void accept(T transaction);
    void reject(T transaction);
}
