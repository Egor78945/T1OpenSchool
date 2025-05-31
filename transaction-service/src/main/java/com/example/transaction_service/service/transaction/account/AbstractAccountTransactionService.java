package com.example.transaction_service.service.transaction.account;

import com.example.transaction_service.environment.account.AccountEnvironment;
import com.example.transaction_service.exception.ProcessingException;
import com.example.transaction_service.model.account.entity.Account;
import com.example.transaction_service.model.transaction.entity.Transaction;
import com.example.transaction_service.model.transaction.status.enumeration.TransactionStatusEnumeration;
import com.example.transaction_service.model.transaction.type.enumeration.TransactionTypeEnumeration;
import com.example.transaction_service.repository.AccountRepository;
import com.example.transaction_service.repository.TransactionRepository;
import com.example.transaction_service.repository.TransactionStatusRepository;
import com.example.transaction_service.repository.TransactionTypeRepository;
import com.example.transaction_service.service.transaction.TransactionService;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Абстрактный класс, реализующий интерфейс, предоставляющий функционал по работе с транзациями {@link TransactionService}
 *
 * @param <T> Тип, являющийся транзакцией {@link Transaction} или его наследником
 */
public abstract class AbstractAccountTransactionService<T extends Transaction> implements TransactionService<T> {
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

    @Override
    public abstract double insert(long recipientId, double amount);

    @Override
    public abstract double transfer(long senderId, long recipientId, double amount);

    @Override
    public abstract boolean isValidInsert(T transaction);

    @Override
    public abstract boolean isValidTransfer(T transaction);

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
    protected Transaction buildInsertTransaction(Account recipient, double amount){
        return Transaction.builder()
                .setTransactionId(buildUUID())
                .setSender(recipient)
                .setRecipient(recipient)
                .setTransactionType(transactionTypeRepository.findById(TransactionTypeEnumeration.INSERT.getId()).get())
                .setTransactionStatus(transactionStatusRepository.findById(TransactionStatusEnumeration.REQUESTED.getId()).get())
                .setAmount(amount)
                .setTime(Timestamp.valueOf(LocalDateTime.now()))
                .build();
    }

    protected Transaction buildTransferTransaction(Account sender, Account recipient, double amount){
        return Transaction.builder()
                .setTransactionId(buildUUID())
                .setSender(sender)
                .setRecipient(recipient)
                .setTransactionType(transactionTypeRepository.findById(TransactionTypeEnumeration.TRANSFER.getId()).get())
                .setTransactionStatus(transactionStatusRepository.findById(TransactionStatusEnumeration.REQUESTED.getId()).get())
                .setAmount(amount)
                .setTime(Timestamp.valueOf(LocalDateTime.now()))
                .build();
    }
}
