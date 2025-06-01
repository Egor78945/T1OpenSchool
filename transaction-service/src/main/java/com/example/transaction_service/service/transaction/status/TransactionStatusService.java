package com.example.transaction_service.service.transaction.status;

import com.example.transaction_service.model.transaction.status.entity.TransactionStatus;

/**
 * Интерфейс, предоставляющий функционал для взаимодействия с {@link TransactionStatus}
 * @param <S> Тип, прнедставляющий {@link TransactionStatus} или его наследника
 */
public interface TransactionStatusService<S extends TransactionStatus> {
    /**
     * Получить {@link TransactionStatus} или его наследника по id
     * @param id Идентификатор
     * @return {@link TransactionStatus}
     */
    S getById(long id);
}
