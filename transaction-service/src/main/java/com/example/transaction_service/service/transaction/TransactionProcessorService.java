package com.example.transaction_service.service.transaction;

import com.example.transaction_service.model.transaction.entity.Transaction;

public interface TransactionProcessorService<T extends Transaction> {
    void accept(T transaction);
    void block(T transaction);
}
