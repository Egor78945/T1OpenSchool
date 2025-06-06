package com.example.transaction_service.service.account.router;

import com.example.transaction_service.model.account.entity.Account;
import com.example.transaction_service.model.account.type.enumeration.AccountTypeEnumeration;
import com.example.transaction_service.service.account.AbstractAccountService;

import java.util.Optional;

/**
 * Интерфейс, предоставляющий функционал для маршрутизации по сервисам, выполняющим действия с клиентскими аккаунтами {@link AbstractAccountService}
 * @param <A> Тип, являющийся абстрактным сервисом по клиентским аккаунтам {@link AbstractAccountService} или его наследником
 */
public interface AccountServiceRouter<A extends AbstractAccountService<? extends Account>> {
    /**
     * Получить абстрактный сервис по клиентским аккаунтам или его наследника {@link AbstractAccountService} по типу клиентского аккаунта {@link AccountTypeEnumeration}
     * @param accountTypeEnumeration тип клиентского аккаунта
     * @return абстрактный сервис по работе с клиентскими аккаутами
     */
    A getByAccountTypeEnumeration(AccountTypeEnumeration accountTypeEnumeration);
}
