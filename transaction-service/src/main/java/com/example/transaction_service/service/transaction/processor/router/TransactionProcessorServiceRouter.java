package com.example.transaction_service.service.transaction.processor.router;

import com.example.transaction_service.model.transaction.entity.Transaction;
import com.example.transaction_service.model.transaction.type.enumeration.TransactionTypeEnumeration;
import com.example.transaction_service.service.transaction.processor.AbstractTransactionProcessorService;

/**
 * Абстрактный класс, предоставляющий функционал для маршрутизации {@link AbstractTransactionProcessorService}
 * @param <T>
 * @param <S>
 */
public interface TransactionProcessorServiceRouter<T extends Transaction, S extends AbstractTransactionProcessorService<T>> {
    /**
     * Получить {@link AbstractTransactionProcessorService} по {@link TransactionTypeEnumeration}
     * @param transactionTypeEnumeration Тип транзакции
     * @return {@link AbstractTransactionProcessorService}, подходящий для обработки транзакции конкретного типа
     */
    S getByTransactionType(TransactionTypeEnumeration transactionTypeEnumeration);
}
