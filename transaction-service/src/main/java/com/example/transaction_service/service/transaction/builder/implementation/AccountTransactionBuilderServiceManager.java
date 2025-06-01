package com.example.transaction_service.service.transaction.builder.implementation;

import com.example.transaction_service.model.account.entity.Account;
import com.example.transaction_service.model.transaction.entity.Transaction;
import com.example.transaction_service.model.transaction.status.entity.TransactionStatus;
import com.example.transaction_service.model.transaction.type.entity.TransactionType;
import com.example.transaction_service.model.transaction.type.enumeration.TransactionTypeEnumeration;
import com.example.transaction_service.service.transaction.AbstractAccountTransactionService;
import com.example.transaction_service.service.transaction.builder.AbstractAccountTransactionBuilderService;
import com.example.transaction_service.service.transaction.type.TransactionTypeService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * Реализация {@link AbstractAccountTransactionBuilderService}
 */
@Service
public class AccountTransactionBuilderServiceManager extends AbstractAccountTransactionBuilderService<Transaction, Account> {
    public AccountTransactionBuilderServiceManager(@Qualifier("debitAccountTransactionServiceManager") AbstractAccountTransactionService<Transaction> transactionService, @Qualifier("transactionTypeServiceManager") TransactionTypeService<TransactionType> transactionTypeService) {
        super(transactionService, transactionTypeService);
    }

    @Override
    public Transaction buildInsert(Account recipient, double amount, TransactionStatus transactionStatus) {
        return Transaction.builder()
                .setTransactionId(buildUUID())
                .setSender(recipient)
                .setRecipient(recipient)
                .setTransactionType(transactionTypeService.getById(TransactionTypeEnumeration.INSERT.getId()))
                .setTransactionStatus(transactionStatus)
                .setAmount(amount)
                .setTime(Timestamp.valueOf(LocalDateTime.now()))
                .build();
    }

    @Override
    public Transaction buildTransfer(Account sender, Account recipient, double amount, TransactionStatus transactionStatus) {
        return Transaction.builder()
                .setTransactionId(buildUUID())
                .setSender(sender)
                .setRecipient(recipient)
                .setTransactionType(transactionTypeService.getById(TransactionTypeEnumeration.TRANSFER.getId()))
                .setTransactionStatus(transactionStatus)
                .setAmount(amount)
                .setTime(Timestamp.valueOf(LocalDateTime.now()))
                .build();
    }
}
