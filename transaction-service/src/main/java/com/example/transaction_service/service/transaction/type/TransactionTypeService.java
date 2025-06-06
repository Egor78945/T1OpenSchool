package com.example.transaction_service.service.transaction.type;

import com.example.transaction_service.model.transaction.type.entity.TransactionType;

/**
 * Интерфейс, предоставляющий функционал для взаимодействия {@link TransactionType} или его наследника
 * @param <T> Тип, представляющий {@link TransactionType} или его наследника
 */
public interface TransactionTypeService<T extends TransactionType> {
    /**
     * Получить {@link TransactionType} или его наследника по id
     * @param id Идентификатор
     * @return {@link TransactionType} или его наследник
     */
    T getById(long id);
}
