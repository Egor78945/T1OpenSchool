package com.example.transaction_service.service.transaction.processor.implementation;

import com.example.transaction_service.exception.ProcessingException;
import com.example.transaction_service.model.account.entity.Account;
import com.example.transaction_service.model.account.status.entity.AccountStatus;
import com.example.transaction_service.model.account.status.entity.enumeration.AccountStatusEnumeration;
import com.example.transaction_service.model.transaction.entity.Transaction;
import com.example.transaction_service.model.transaction.status.enumeration.TransactionStatusEnumeration;
import com.example.transaction_service.service.account.AbstractAccountService;
import com.example.transaction_service.service.account.status.AccountStatusService;
import com.example.transaction_service.service.transaction.AbstractAccountTransactionService;
import com.example.transaction_service.service.transaction.processor.AbstractTransactionProcessorService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class TransferAbstractTransactionProcessorServiceManager extends AbstractTransactionProcessorService<Transaction> {
    private final AbstractAccountTransactionService<Transaction> transactionService;
    private final AccountStatusService<AccountStatus> accountStatusService;
    private final AbstractAccountService<Account> accountService;

    public TransferAbstractTransactionProcessorServiceManager(@Qualifier("debitAccountTransactionServiceManager") AbstractAccountTransactionService<Transaction> transactionService, @Qualifier("accountStatusServiceManager") AccountStatusService<AccountStatus> accountStatusService, @Qualifier("debitAccountServiceManager") AbstractAccountService<Account> accountService) {
        this.transactionService = transactionService;
        this.accountStatusService = accountStatusService;
        this.accountService = accountService;
    }

    @Override
    public void process(Transaction transaction) {
        transactionProcessorMap.get(TransactionStatusEnumeration.getById(transaction.getTransactionStatus().getId())).accept(transaction);
    }

    @Override
    protected void accept(Transaction transaction) {
        if (transaction.getTransactionStatus().getId().equals(TransactionStatusEnumeration.ACCEPTED.getId())) {
            if (transactionService.existsById(transaction.getId())) {
                transactionService.update(transaction);
            } else {
                throw new ProcessingException(String.format("accepting transaction is not exists\nTransaction : %s", transaction));
            }
        } else {
            throw new ProcessingException("transaction is not accepted");
        }
    }

    @Override
    protected void block(Transaction transaction) {
        if (transaction.getTransactionStatus().getId().equals(TransactionStatusEnumeration.BLOCKED.getId())) {
            Account sender = transaction.getSender();
            Account recipient = transaction.getRecipient();
            sender.setAccountStatus(accountStatusService.getById(AccountStatusEnumeration.BLOCKED.getId()));
            sender.setFrozen_balance(sender.getFrozen_balance() + transaction.getAmount());
            if (recipient.getBalance() >= transaction.getAmount()) {
                recipient.setBalance(recipient.getBalance() - transaction.getAmount());
            }
            accountService.update(sender);
            accountService.update(recipient);
            transactionService.update(transaction);
        } else {
            throw new ProcessingException("transaction is not blocked");
        }
    }

    @Override
    protected void reject(Transaction transaction) {
        if (transaction.getTransactionStatus().getId().equals(TransactionStatusEnumeration.REJECTED.getId())) {
            Account sender = transaction.getSender();
            Account recipient = transaction.getRecipient();
            if(recipient.getBalance() >= transaction.getAmount()){
                sender.setBalance(sender.getBalance() + transaction.getAmount());
                recipient.setBalance(recipient.getBalance() - transaction.getAmount());
            } else {
                sender.setAccountStatus(accountStatusService.getById(AccountStatusEnumeration.BLOCKED.getId()));
                recipient.setAccountStatus(accountStatusService.getById(AccountStatusEnumeration.BLOCKED.getId()));
            }
            accountService.update(sender);
            accountService.update(recipient);
            transactionService.update(transaction);
        } else {
            throw new ProcessingException("transaction is not rejected");
        }
    }
}
