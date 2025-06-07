package com.example.transaction_manager_service.service.transaction.processor;

import com.example.transaction_manager_service.model.transaction.entity.Transaction;

/**
 * Интерфейс, предоставляющий функционал для обработки транзакций
 * @param <T> Тип, представляющий {@link Transaction} или его наследника
 */
public interface TransactionProcessorService<T extends Transaction> {
    /**
     * Одобрить транзакцию
     * @param transaction Объект {@link Transaction} или его наследник
     */
    void accept(T transaction);
    /**
     * Заблокировать транзакцию
     * @param transaction Объект {@link Transaction} или его наследник
     */
    void block(T transaction);
}
