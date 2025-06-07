package com.example.transaction_service.service.transaction.processor;

import com.example.transaction_service.model.transaction.entity.Transaction;
import com.example.transaction_service.model.transaction.status.enumeration.TransactionStatusEnumeration;

import java.util.Map;
import java.util.function.Consumer;

/**
 * Абстрактный класс, предоставляющий функционал для обработки транзакций, в зависимости от их статуса
 * @param <T> Тип, представляющий {@link Transaction} или его наследника
 */
public abstract class AbstractTransactionProcessorService<T extends Transaction> {
    protected final Map<TransactionStatusEnumeration, Consumer<T>> transactionProcessorMap;

    public AbstractTransactionProcessorService() {
        this.transactionProcessorMap = Map.of(
                TransactionStatusEnumeration.ACCEPTED, this::accept,
                TransactionStatusEnumeration.BLOCKED, this::block,
                TransactionStatusEnumeration.REJECTED, this::reject
        );
    }

    /**
     * Обработать транзакцию
     * @param transaction Объект {@link Transaction} или его наследник
     */
    public abstract void process(T transaction);
    /**
     * Одобрить транзакцию
     * @param transaction Объект {@link Transaction} или его наследник
     */
    protected abstract void accept(T transaction);
    /**
     * Заблокировать транзакцию
     * @param transaction Объект {@link Transaction} или его наследник
     */
    protected abstract void block(T transaction);
    /**
     * Откатить транзакцию
     * @param transaction Объект {@link Transaction} или его наследник
     */
    protected abstract void reject(T transaction);
}
