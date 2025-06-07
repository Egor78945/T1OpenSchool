package com.example.transaction_manager_service.service.transaction;

import com.example.transaction_manager_service.model.transaction.entity.Transaction;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

/**
 * Интерфейс, предоставляющий функционал для взаимодейсвтия с {@link Transaction}
 * @param <T> Тип, представляющий {@link Transaction} или его наследника
 */
public interface TransactionService<T extends Transaction> {
    /**
     * Сохранить уникальную транзакцию
     * @param transaction Объект {@link Transaction} или его наследник
     * @return Сохранённый объект {@link Transaction} или его наследник
     */
    T save(T transaction);
    /**
     * Получить {@link Transaction} или его наследника по идентификатору
     * @param id Идентификатор {@link Transaction} или его наследника
     * @return Существующий объект {@link Transaction} или его наследник
     */
    T getById(long id);
    /**
     * Получить {@link Transaction} или его наследника по уникальному коду транзакции
     * @param uuid Уникальный код {@link Transaction} или его наследника
     * @return Существующий объект {@link Transaction} или его наследник
     */
    T getByTransactionId(UUID uuid);

    /**
     * Получить все транзакции определённого клиента, совершённые после определённого времени
     * @param id Идентификатор клиента
     * @param afterTime Время, после которого транзакция должна быть получена
     * @return Список {@link Transaction}
     */
    List<T> getBySenderIdAfter(long id, Timestamp afterTime);
}
