package com.example.transaction_service.service.account.type;

import com.example.transaction_service.model.account.type.entity.AccountType;

/**
 * Интерфейс, предоставляющий функционал для взаимодействия с {@link AccountType}
 * @param <T> Тип, являющийся {@link AccountType} или его наследником
 */
public interface AccountTypeService<T extends AccountType>{
    /**
     * Получить {@link AccountType} или его наследника по id
     * @param id Идентификатор {@link AccountType}
     * @return {@link AccountType} или его наследник
     */
    T getById(long id);
}
