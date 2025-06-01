package com.example.transaction_service.service.transaction.implementation;

import com.example.transaction_service.environment.account.AccountEnvironment;
import com.example.transaction_service.exception.TransactionException;
import com.example.transaction_service.model.account.entity.Account;
import com.example.transaction_service.model.account.status.entity.enumeration.AccountStatusEnumeration;
import com.example.transaction_service.model.account.type.enumeration.AccountTypeEnumeration;
import com.example.transaction_service.model.transaction.entity.Transaction;
import com.example.transaction_service.repository.AccountRepository;
import com.example.transaction_service.repository.TransactionRepository;
import com.example.transaction_service.repository.TransactionStatusRepository;
import com.example.transaction_service.repository.TransactionTypeRepository;
import com.example.transaction_service.service.common.aop.annotation.LogDatasourceError;
import com.example.transaction_service.service.common.aop.annotation.Metric;
import com.example.transaction_service.service.transaction.AbstractDebitAccountTransactionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Реализация абстрактной реализации сервиса по работе странзакциями {@link AbstractDebitAccountTransactionService} типа <b>DEBIT</b>
 */
@Service
public class DebitAccountTransactionServiceManager extends AbstractDebitAccountTransactionService<Transaction> {
    public DebitAccountTransactionServiceManager(TransactionRepository transactionRepository, AccountRepository accountRepository, TransactionTypeRepository transactionTypeRepository, AccountEnvironment accountEnvironment, TransactionStatusRepository transactionStatusRepository) {
        super(transactionRepository, accountRepository, accountEnvironment, transactionTypeRepository, transactionStatusRepository);
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
    @LogDatasourceError
    @Metric
    public void insert(Transaction transaction) {
        Account recipient = transaction.getRecipient();

        if (isValidInsert(transaction)) {
            recipient.setBalance(recipient.getBalance() + transaction.getAmount());
            transactionRepository.save(transaction);
            accountRepository.save(recipient);
        } else {
            throw new TransactionException(String.format("insert transaction can not be done successfully\nRecipient : %s\ntransaction amount : %s", recipient, transaction.getAmount()));
        }
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
    @LogDatasourceError
    @Metric
    public void transfer(Transaction transaction) {
        Account recipient = transaction.getRecipient();
        Account sender = transaction.getSender();
        if (isValidTransfer(transaction)) {
            recipient.setBalance(recipient.getBalance() + transaction.getAmount());
            sender.setBalance(sender.getBalance() - transaction.getAmount());
            transactionRepository.save(transaction);
            accountRepository.saveAll(List.of(sender, recipient));
        } else {
            throw new TransactionException(String.format("insert transaction can not be done successfully\nSender : %s\nRecipient : %s\ntransaction amount : %s", sender, recipient, transaction.getAmount()));
        }
    }

    @Override
    public boolean isValidInsert(Transaction transaction) {
        return transaction.getRecipient().getAccountType().getId() == AccountTypeEnumeration.DEBIT.getId() &&
                transaction.getAmount() <= accountEnvironment.getACCOUNT_TRANSACTION_MAX_AMOUNT() &&
                transaction.getAmount() >= accountEnvironment.getACCOUNT_TRANSACTION_MIN_AMOUNT() &&
                transaction.getRecipient().getBalance() + transaction.getAmount() <= accountEnvironment.getACCOUNT_BALANCE_MAX_AMOUNT() &&
                transaction.getRecipient().getAccountStatus().getId().equals(AccountStatusEnumeration.OPEN.getId());
    }

    @Override
    public boolean isValidTransfer(Transaction transaction) {
        return !transaction.getSender().getId().equals(transaction.getRecipient().getId()) &&
                transaction.getSender().getAccountType().getId() == AccountTypeEnumeration.DEBIT.getId() &&
                transaction.getAmount() <= accountEnvironment.getACCOUNT_TRANSACTION_MAX_AMOUNT() &&
                transaction.getAmount() >= accountEnvironment.getACCOUNT_TRANSACTION_MIN_AMOUNT() &&
                transaction.getRecipient().getBalance() + transaction.getAmount() <= accountEnvironment.getACCOUNT_BALANCE_MAX_AMOUNT() &&
                transaction.getSender().getBalance() - transaction.getAmount() >= 0 &&
                transaction.getSender().getAccountStatus().getId().equals(AccountStatusEnumeration.OPEN.getId()) &&
                transaction.getRecipient().getAccountStatus().getId().equals(AccountStatusEnumeration.OPEN.getId());
    }
}
