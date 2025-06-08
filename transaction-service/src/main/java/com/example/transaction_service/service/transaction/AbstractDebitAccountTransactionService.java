package com.example.transaction_service.service.transaction;

import com.example.transaction_service.environment.account.AccountEnvironment;
import com.example.transaction_service.model.client.entity.Client;
import com.example.transaction_service.model.transaction.entity.Transaction;
import com.example.transaction_service.model.user.entity.User;
import com.example.transaction_service.repository.AccountRepository;
import com.example.transaction_service.repository.TransactionRepository;
import com.example.transaction_service.repository.TransactionStatusRepository;
import com.example.transaction_service.repository.TransactionTypeRepository;
import com.example.transaction_service.service.client.AbstractClientService;
import com.example.transaction_service.service.common.authentication.AuthenticationContextService;

/**
 * Реализация абстрактного сервиса по работе с транзакциями между клиентскими аккаунтами {@link AbstractAccountTransactionService}
 * для работы с клиентскими аккаунтами типа <b>DEBIT</b>
 * @param <T> Тип, являющийся транзакцией {@link Transaction} или её наследником
 */
public abstract class AbstractDebitAccountTransactionService<T extends Transaction> extends AbstractAccountTransactionService<T> {
    public AbstractDebitAccountTransactionService(AuthenticationContextService<User> userAuthenticationContextService, TransactionRepository transactionRepository, AccountRepository accountRepository, AccountEnvironment accountEnvironment, TransactionTypeRepository transactionTypeRepository, TransactionStatusRepository transactionStatusRepository) {
        super(userAuthenticationContextService, transactionRepository, accountRepository,accountEnvironment, transactionTypeRepository, transactionStatusRepository);
    }

    @Override
    public abstract T insert(Transaction transaction);

    @Override
    public abstract T transfer(Transaction transaction);

    @Override
    public abstract boolean isValidInsert(T transaction);

    @Override
    public abstract boolean isValidTransfer(T transaction);
}
