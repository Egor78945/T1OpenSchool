package com.example.transaction_service.service.account.status;

import com.example.transaction_service.model.account.status.entity.AccountStatus;

/**
 * Интерфейс, предоставляющий функционал для взаимодействия с {@link AccountStatus}
 * @param <S> Тип, являющийся {@link AccountStatus} или его наследником
 */
public interface AccountStatusService<S extends AccountStatus> {
    /**
     * Получить {@link AccountStatus} или его наследника по id
     * @param id Идентификатор {@link AccountStatus}
     * @return {@link AccountStatus} или его наследник
     */
    S getById(long id);
}
