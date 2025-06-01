package com.example.transaction_service.service.transaction.router;

import com.example.transaction_service.model.account.type.enumeration.AccountTypeEnumeration;
import com.example.transaction_service.model.transaction.entity.Transaction;
import com.example.transaction_service.service.transaction.AbstractAccountTransactionService;

import java.util.Optional;

/**
 * Интефрейс, предоставляющий функционал по маршрутизации абстрактных сервисов по работе
 * с транзакциями между клиентскими аккаунтами {@link AbstractAccountTransactionService}
 * @param <T> Тип, являющийся абстрактным сервисом по работе
 *           с транзакциями между клиентскими аккаунтами {@link AbstractAccountTransactionService} или его наследником
 */
public interface AccountTransactionServiceRouter<T extends AbstractAccountTransactionService<Transaction>> {
    /**
     * Получить сервис по типу клиентского аккаунта {@link AccountTypeEnumeration}
     * @param accountTypeEnumeration тип клиентского аккаунта {@link AccountTypeEnumeration}
     * @return Абстрактный сервис по работе с транзакциями
     */
    T getByAccountTypeEnumeration(AccountTypeEnumeration accountTypeEnumeration);
}
