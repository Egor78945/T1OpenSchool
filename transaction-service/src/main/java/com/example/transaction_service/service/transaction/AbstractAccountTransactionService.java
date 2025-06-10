package com.example.transaction_service.service.transaction;

import com.example.transaction_service.environment.account.AccountEnvironment;
import com.example.transaction_service.exception.ProcessingException;
import com.example.transaction_service.model.account.entity.Account;
import com.example.transaction_service.model.client.entity.Client;
import com.example.transaction_service.model.transaction.entity.Transaction;
import com.example.transaction_service.model.user.entity.User;
import com.example.transaction_service.repository.*;
import com.example.transaction_service.service.client.AbstractClientService;
import com.example.transaction_service.service.common.authentication.AuthenticationContextService;
import com.example.transaction_service.service.transaction.builder.AbstractAccountTransactionBuilderService;
import org.springframework.beans.factory.annotation.Qualifier;

import java.sql.Timestamp;
import java.util.UUID;

/**
 * Абстрактный класс, предоставляющий функционал по работе с транзациями
 *
 * @param <T> Тип, являющийся транзакцией {@link Transaction} или его наследником
 */
public abstract class AbstractAccountTransactionService<T extends Transaction> {
    protected final TransactionRepository transactionRepository;
    protected final AccountRepository accountRepository;
    protected final AccountEnvironment accountEnvironment;
    protected final TransactionTypeRepository transactionTypeRepository;
    protected final TransactionStatusRepository transactionStatusRepository;
    protected final AuthenticationContextService<User> userAuthenticationContextService;

    public AbstractAccountTransactionService(AuthenticationContextService<User> userAuthenticationContextService, TransactionRepository transactionRepository, AccountRepository accountRepository, AccountEnvironment accountEnvironment, TransactionTypeRepository transactionTypeRepository, TransactionStatusRepository transactionStatusRepository) {
        this.userAuthenticationContextService = userAuthenticationContextService;
        this.transactionRepository = transactionRepository;
        this.accountRepository = accountRepository;
        this.accountEnvironment = accountEnvironment;
        this.transactionTypeRepository = transactionTypeRepository;
        this.transactionStatusRepository = transactionStatusRepository;
    }

    public abstract T insert(T transaction);

    public abstract T transfer(T transaction);

    public abstract boolean isValidInsert(T transaction);

    public abstract boolean isValidTransfer(T transaction);

    public boolean existsById(long id){
        return transactionRepository.existsById(id);
    }

    public boolean existsByTransactionId(UUID uuid){
        return transactionRepository.existsTransactionByTransaction_id(uuid);
    }

    public Transaction update(Transaction transaction) {
        if(transaction.getId() != null && transaction.getTransaction_id() != null && transactionRepository.existsById(transaction.getId()) && transactionRepository.existsTransactionByTransaction_id(transaction.getTransaction_id())){
            return transactionRepository.save(transaction);
        } else {
            throw new ProcessingException(String.format("transaction is not able to update\nTransaction : %s", transaction));
        }
    }

    public Transaction save(Transaction transaction){
        if(transaction.getId() == null && transaction.getTransaction_id() == null) {
            transaction.setTransaction_id(buildUUID());
            return transactionRepository.save(transaction);
        } else {
            throw new ProcessingException(String.format("transaction is not able to save\nTransaction : %s", transaction));
        }
    }

    public int getBySenderAfterTime(UUID senderId, Timestamp after){
        return transactionRepository.countRejectedTransactionsBySenderPerTimeByAccountId(senderId, after);
    }

    public int getByRecipientAfterTime(UUID recipientId, Timestamp after){
        return transactionRepository.countRejectedTransactionsByRecipientPerTimeByAccountId(recipientId, after);
    }

    public UUID buildUUID(){
        UUID uuid = UUID.randomUUID();
        for(int i = 0; i < 10; i++){
            if(transactionRepository.existsTransactionByTransaction_id(uuid)){
                uuid = UUID.randomUUID();
            } else {
                return uuid;
            }
        }
        throw new ProcessingException("uuid is not generated");
    }
}
