package com.example.transaction_service.service.transaction.router;

import com.example.transaction_service.exception.NotFoundException;
import com.example.transaction_service.model.account.type.enumeration.AccountTypeEnumeration;
import com.example.transaction_service.model.transaction.entity.Transaction;
import com.example.transaction_service.service.transaction.AbstractAccountTransactionService;
import com.example.transaction_service.service.transaction.AbstractCreditAccountTransactionService;
import com.example.transaction_service.service.transaction.AbstractDebitAccountTransactionService;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

/**
 * Реализация маршрутизатора абстрактных сервисов по работе с транзакциями между клиентскими аккаунтами {@link AccountTransactionServiceRouter}
 */
@Service
public class AccountTransactionServiceRouterManager implements AccountTransactionServiceRouter<AbstractAccountTransactionService<Transaction>> {
    private final Map<AccountTypeEnumeration, AbstractAccountTransactionService<Transaction>> transactionServiceByAccountTypeEnumeration;

    public AccountTransactionServiceRouterManager(AbstractDebitAccountTransactionService<Transaction> debitAccountTransactionService, AbstractCreditAccountTransactionService<Transaction> creditAccountTransactionService) {
        this.transactionServiceByAccountTypeEnumeration = Map.of(AccountTypeEnumeration.DEBIT, debitAccountTransactionService, AccountTypeEnumeration.CREDIT, creditAccountTransactionService);
    }

    @Override
    public AbstractAccountTransactionService<Transaction> getByAccountTypeEnumeration(AccountTypeEnumeration accountTypeEnumeration) {
        return Optional.ofNullable(transactionServiceByAccountTypeEnumeration.get(accountTypeEnumeration)).orElseThrow(() -> new NotFoundException(String.format("unknown account transaction service by account type enumeration\nAccountTypeEnumeration : %s", accountTypeEnumeration)));
    }
}
