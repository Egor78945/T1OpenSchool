package com.example.transaction_service.service.transaction.processor;

import com.example.transaction_service.model.transaction.entity.Transaction;
import com.example.transaction_service.model.transaction.status.enumeration.TransactionStatusEnumeration;

import java.util.Map;
import java.util.function.Consumer;

public abstract class AbstractTransactionProcessorService<T extends Transaction> {
    protected final Map<TransactionStatusEnumeration, Consumer<T>> transactionProcessorMap;

    public AbstractTransactionProcessorService() {
        this.transactionProcessorMap = Map.of(
                TransactionStatusEnumeration.ACCEPTED, this::accept,
                TransactionStatusEnumeration.BLOCKED, this::block,
                TransactionStatusEnumeration.REJECTED, this::reject
        );
    }

    public abstract void process(T transaction);
    protected abstract void accept(T transaction);
    protected abstract void block(T transaction);
    protected abstract void reject(T transaction);
}
