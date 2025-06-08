package com.example.transaction_service.service.account.router.implementation;

import com.example.transaction_service.exception.NotFoundException;
import com.example.transaction_service.model.account.entity.Account;
import com.example.transaction_service.model.account.type.enumeration.AccountTypeEnumeration;
import com.example.transaction_service.service.account.AbstractAccountService;
import com.example.transaction_service.service.account.AbstractCreditAccountService;
import com.example.transaction_service.service.account.AbstractDebitAccountService;
import com.example.transaction_service.service.account.router.AccountServiceRouter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Optional;

/**
 * Реализация интерфейса для маршрутизации {@link AccountServiceRouter} по сервисам клиентских аккаунтов
 */
@Component
public class AccountServiceRouterManager implements AccountServiceRouter<AbstractAccountService<Account>> {
    private final Map<AccountTypeEnumeration, AbstractAccountService<Account>> accountServiceByAccountTypeEnum;

    public AccountServiceRouterManager(@Qualifier("debitAccountServiceManager") AbstractDebitAccountService<Account> debitAccountServiceManager, @Qualifier("creditAccountServiceManager") AbstractCreditAccountService<Account> creditAccountServiceManager) {
        this.accountServiceByAccountTypeEnum = Map.of(AccountTypeEnumeration.DEBIT, debitAccountServiceManager, AccountTypeEnumeration.CREDIT, creditAccountServiceManager);
    }

    @Override
    public AbstractAccountService<Account> getByAccountTypeEnumeration(AccountTypeEnumeration accountTypeEnumeration) {
        return Optional.ofNullable(accountServiceByAccountTypeEnum.get(accountTypeEnumeration)).orElseThrow(() -> new NotFoundException(String.format("account service is not found by account type enumeration\nAccountTypeEnumeration : %s", accountTypeEnumeration)));
    }
}
