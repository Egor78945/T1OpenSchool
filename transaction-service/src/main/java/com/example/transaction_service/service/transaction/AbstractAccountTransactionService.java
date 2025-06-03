package com.example.transaction_service.service.transaction;

import com.example.transaction_service.environment.account.AccountEnvironment;
import com.example.transaction_service.exception.ProcessingException;
import com.example.transaction_service.model.transaction.entity.Transaction;
import com.example.transaction_service.repository.AccountRepository;
import com.example.transaction_service.repository.TransactionRepository;
import com.example.transaction_service.repository.TransactionStatusRepository;
import com.example.transaction_service.repository.TransactionTypeRepository;

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

    public AbstractAccountTransactionService(TransactionRepository transactionRepository, AccountRepository accountRepository, AccountEnvironment accountEnvironment, TransactionTypeRepository transactionTypeRepository, TransactionStatusRepository transactionStatusRepository) {
        this.transactionRepository = transactionRepository;
        this.accountRepository = accountRepository;
        this.accountEnvironment = accountEnvironment;
        this.transactionTypeRepository = transactionTypeRepository;
        this.transactionStatusRepository = transactionStatusRepository;
    }

    public abstract T insert(T transaction);

    public abstract void transfer(T transaction);

    public abstract boolean isValidInsert(T transaction);

    public abstract boolean isValidTransfer(T transaction);

    public boolean existsById(long id){
        return transactionRepository.existsById(id);
    }

    public boolean existsByTransactionId(UUID uuid){
        return transactionRepository.existsTransactionByTransaction_id(uuid);
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
