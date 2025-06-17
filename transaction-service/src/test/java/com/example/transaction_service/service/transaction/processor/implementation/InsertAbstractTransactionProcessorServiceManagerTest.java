package com.example.transaction_service.service.transaction.processor.implementation;

import com.example.transaction_service.exception.ProcessingException;
import com.example.transaction_service.model.account.entity.Account;
import com.example.transaction_service.model.account.status.entity.AccountStatus;
import com.example.transaction_service.model.account.status.entity.enumeration.AccountStatusEnumeration;
import com.example.transaction_service.model.account.type.entity.AccountType;
import com.example.transaction_service.model.account.type.enumeration.AccountTypeEnumeration;
import com.example.transaction_service.model.client.entity.Client;
import com.example.transaction_service.model.client.status.entity.ClientStatus;
import com.example.transaction_service.model.client.status.enumeration.ClientStatusEnumeration;
import com.example.transaction_service.model.transaction.entity.Transaction;
import com.example.transaction_service.model.transaction.status.entity.TransactionStatus;
import com.example.transaction_service.model.transaction.status.enumeration.TransactionStatusEnumeration;
import com.example.transaction_service.model.transaction.type.entity.TransactionType;
import com.example.transaction_service.model.transaction.type.enumeration.TransactionTypeEnumeration;
import com.example.transaction_service.model.user.entity.User;
import com.example.transaction_service.service.account.AbstractAccountService;
import com.example.transaction_service.service.account.status.AccountStatusService;
import com.example.transaction_service.service.transaction.AbstractAccountTransactionService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Timestamp;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
public class InsertAbstractTransactionProcessorServiceManagerTest {
    @Mock
    private AbstractAccountTransactionService<Transaction> transactionService;
    @Mock
    private AccountStatusService<AccountStatus> accountStatusService;
    @Mock
    private AbstractAccountService<Account> accountService;
    @InjectMocks
    private InsertAbstractTransactionProcessorServiceManager insertAbstractTransactionProcessorServiceManager;
    private static Transaction acceptedTransaction;
    private static Transaction blockTransaction;
    private static Transaction rejectTransaction;
    private static Transaction cancelledTransaction;

    @BeforeAll
    public static void initAcceptedTransaction() {
        User recipientUser = new User("email", "password");
        recipientUser.setId(1L);

        Client recipientClient = new Client("name", "surname", "patronymic", recipientUser, new ClientStatus(ClientStatusEnumeration.ACTIVE.getId(), ClientStatusEnumeration.ACTIVE.name()));
        recipientClient.setId(1L);

        Account recipientAccount = new Account(recipientClient, UUID.randomUUID(), new AccountType(AccountTypeEnumeration.DEBIT.getId(), AccountTypeEnumeration.DEBIT.name()), new AccountStatus(AccountStatusEnumeration.OPEN.getId(), AccountStatusEnumeration.OPEN.name()));
        recipientAccount.setId(1L);

        acceptedTransaction = Transaction.builder()
                .setTransactionStatus(new TransactionStatus(TransactionStatusEnumeration.ACCEPTED.getId(), TransactionStatusEnumeration.ACCEPTED.name()))
                .setTransactionType(new TransactionType(TransactionTypeEnumeration.INSERT.getId(), TransactionTypeEnumeration.INSERT.name()))
                .setRecipient(recipientAccount)
                .setTime(new Timestamp(1))
                .setAmount(1000)
                .build();
        acceptedTransaction.setId(1L);
    }

    @BeforeAll
    public static void initBlockedTransaction() {
        User recipientUser = new User("email", "password");
        recipientUser.setId(1L);

        Client recipientClient = new Client("name", "surname", "patronymic", recipientUser, new ClientStatus(ClientStatusEnumeration.ACTIVE.getId(), ClientStatusEnumeration.ACTIVE.name()));
        recipientClient.setId(1L);

        Account recipientAccount = new Account(recipientClient, UUID.randomUUID(), new AccountType(AccountTypeEnumeration.DEBIT.getId(), AccountTypeEnumeration.DEBIT.name()), new AccountStatus(AccountStatusEnumeration.OPEN.getId(), AccountStatusEnumeration.OPEN.name()));
        recipientAccount.setId(1L);

        blockTransaction = Transaction.builder()
                .setTransactionStatus(new TransactionStatus(TransactionStatusEnumeration.BLOCKED.getId(), TransactionStatusEnumeration.BLOCKED.name()))
                .setTransactionType(new TransactionType(TransactionTypeEnumeration.INSERT.getId(), TransactionTypeEnumeration.INSERT.name()))
                .setRecipient(recipientAccount)
                .setTime(new Timestamp(1))
                .setAmount(1000)
                .build();
        blockTransaction.setId(2L);
    }

    @BeforeAll
    public static void initRejectTransaction() {
        User recipientUser = new User("email", "password");
        recipientUser.setId(1L);

        Client recipientClient = new Client("name", "surname", "patronymic", recipientUser, new ClientStatus(ClientStatusEnumeration.ACTIVE.getId(), ClientStatusEnumeration.ACTIVE.name()));
        recipientClient.setId(1L);

        Account recipientAccount = new Account(recipientClient, UUID.randomUUID(), new AccountType(AccountTypeEnumeration.DEBIT.getId(), AccountTypeEnumeration.DEBIT.name()), new AccountStatus(AccountStatusEnumeration.OPEN.getId(), AccountStatusEnumeration.OPEN.name()));
        recipientAccount.setId(1L);

        rejectTransaction = Transaction.builder()
                .setTransactionStatus(new TransactionStatus(TransactionStatusEnumeration.REJECTED.getId(), TransactionStatusEnumeration.REJECTED.name()))
                .setTransactionType(new TransactionType(TransactionTypeEnumeration.INSERT.getId(), TransactionTypeEnumeration.INSERT.name()))
                .setRecipient(recipientAccount)
                .setTime(new Timestamp(1))
                .setAmount(1000)
                .build();
        rejectTransaction.setId(3L);
    }

    @BeforeAll
    public static void initCancelledTransaction() {
        User recipientUser = new User("email", "password");
        recipientUser.setId(1L);

        Client recipientClient = new Client("name", "surname", "patronymic", recipientUser, new ClientStatus(ClientStatusEnumeration.ACTIVE.getId(), ClientStatusEnumeration.ACTIVE.name()));
        recipientClient.setId(1L);

        Account recipientAccount = new Account(recipientClient, UUID.randomUUID(), new AccountType(AccountTypeEnumeration.DEBIT.getId(), AccountTypeEnumeration.DEBIT.name()), new AccountStatus(AccountStatusEnumeration.OPEN.getId(), AccountStatusEnumeration.OPEN.name()));
        recipientAccount.setId(1L);

        cancelledTransaction = Transaction.builder()
                .setTransactionStatus(new TransactionStatus(TransactionStatusEnumeration.CANCELLED.getId(), TransactionStatusEnumeration.CANCELLED.name()))
                .setTransactionType(new TransactionType(TransactionTypeEnumeration.INSERT.getId(), TransactionTypeEnumeration.INSERT.name()))
                .setRecipient(recipientAccount)
                .setTime(new Timestamp(1))
                .setAmount(1000)
                .build();
        cancelledTransaction.setId(4L);

    }

    @Test
    public void process_getUnknownProcessor_throwsNullPointerException() {
        Assertions.assertThrows(NullPointerException.class, () -> insertAbstractTransactionProcessorServiceManager.process(cancelledTransaction));
    }

    @Test
    public void accept_notAcceptedTransactionStatus_throwsProcessingException() {
        Assertions.assertThrows(ProcessingException.class, () -> insertAbstractTransactionProcessorServiceManager.accept(cancelledTransaction));
    }

    @Test
    public void accept_transactionIsNotExists_throwsProcessingException() {
        Mockito.when(transactionService.existsById(acceptedTransaction.getId())).thenReturn(false);
        Assertions.assertThrows(ProcessingException.class, () -> insertAbstractTransactionProcessorServiceManager.accept(acceptedTransaction));
    }

    @Test
    public void accept_acceptTransaction_success() {
        Mockito.when(transactionService.existsById(acceptedTransaction.getId())).thenReturn(true);
        Mockito.when(transactionService.update(acceptedTransaction)).thenReturn(acceptedTransaction);
        Assertions.assertDoesNotThrow(() -> insertAbstractTransactionProcessorServiceManager.accept(acceptedTransaction));
    }

    @Test
    public void block_notBlockedTransactionStatus_throwsProcessingException() {
        Assertions.assertThrows(ProcessingException.class, () -> insertAbstractTransactionProcessorServiceManager.block(cancelledTransaction));
    }

    @Test
    public void block_blockTransaction_success() {
        Mockito.when(accountStatusService.getById(AccountStatusEnumeration.BLOCKED.getId())).thenReturn(new AccountStatus(AccountStatusEnumeration.BLOCKED.getId(), AccountStatusEnumeration.BLOCKED.name()));
        Mockito.when(accountService.update(blockTransaction.getRecipient())).thenReturn(blockTransaction.getRecipient());
        Mockito.when(transactionService.update(blockTransaction)).thenReturn(blockTransaction);
        Assertions.assertDoesNotThrow(() -> insertAbstractTransactionProcessorServiceManager.block(blockTransaction));
    }

    @Test
    public void reject_notRejectedTransactionStatus_throwsProcessingException() {
        Assertions.assertThrows(ProcessingException.class, () -> insertAbstractTransactionProcessorServiceManager.reject(cancelledTransaction));
    }

    @Test
    public void reject_rejectTransaction_success() {
        Mockito.when(accountStatusService.getById(AccountStatusEnumeration.BLOCKED.getId())).thenReturn(new AccountStatus(AccountStatusEnumeration.BLOCKED.getId(), AccountStatusEnumeration.BLOCKED.name()));
        Mockito.when(accountService.update(rejectTransaction.getRecipient())).thenReturn(rejectTransaction.getRecipient());
        Mockito.when(transactionService.update(rejectTransaction)).thenReturn(rejectTransaction);
        Assertions.assertDoesNotThrow(() -> insertAbstractTransactionProcessorServiceManager.reject(rejectTransaction));
    }
}
