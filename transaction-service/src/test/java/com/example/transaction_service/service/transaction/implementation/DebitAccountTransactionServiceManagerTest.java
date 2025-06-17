package com.example.transaction_service.service.transaction.implementation;

import com.example.transaction_service.environment.account.AccountEnvironment;
import com.example.transaction_service.exception.TransactionException;
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
import com.example.transaction_service.repository.AccountRepository;
import com.example.transaction_service.repository.TransactionRepository;
import com.example.transaction_service.repository.TransactionStatusRepository;
import com.example.transaction_service.repository.TransactionTypeRepository;
import com.example.transaction_service.service.security.user.authentication.UserAuthenticationContextServiceManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
public class DebitAccountTransactionServiceManagerTest {
    @Mock
    private UserAuthenticationContextServiceManager userAuthenticationContextServiceManager;
    @Mock
    private TransactionRepository transactionRepository;
    @Mock
    private AccountRepository accountRepository;
    @Mock
    private AccountEnvironment accountEnvironment;
    @Mock
    private TransactionTypeRepository transactionTypeRepository;
    @Mock
    private TransactionStatusRepository transactionStatusRepository;
    @InjectMocks
    private DebitAccountTransactionServiceManager debitAccountTransactionServiceManager;
    private static Transaction insertTransaction;
    private static Transaction transferTransaction;

    @BeforeAll
    public static void initInsertTransaction(){
        User recipientUser = new User("email", "password");
        recipientUser.setId(1L);

        Client recipientClient = new Client("name", "surname", "patronymic", recipientUser, new ClientStatus(ClientStatusEnumeration.ACTIVE.getId(), ClientStatusEnumeration.ACTIVE.name()));
        recipientClient.setId(1L);

        Account recipientAccount = new Account(recipientClient, UUID.randomUUID(), new AccountType(AccountTypeEnumeration.DEBIT.getId(), AccountTypeEnumeration.DEBIT.name()), new AccountStatus(AccountStatusEnumeration.OPEN.getId(), AccountStatusEnumeration.OPEN.name()));
        recipientAccount.setId(1L);

        insertTransaction = Transaction.builder()
                .setTransactionStatus(new TransactionStatus(TransactionStatusEnumeration.ACCEPTED.getId(), TransactionStatusEnumeration.ACCEPTED.name()))
                .setTransactionType(new TransactionType(TransactionTypeEnumeration.INSERT.getId(), TransactionTypeEnumeration.INSERT.name()))
                .setRecipient(recipientAccount)
                .setTime(new Timestamp(1))
                .setAmount(1000)
                .build();
    }

    @BeforeAll
    public static void initTransferTransaction(){
        User recipientUser = new User("email", "password");
        recipientUser.setId(1L);
        User senderUser = new User("email", "password");
        senderUser.setId(2L);

        Client recipientClient = new Client("name", "surname", "patronymic", recipientUser, new ClientStatus(ClientStatusEnumeration.ACTIVE.getId(), ClientStatusEnumeration.ACTIVE.name()));
        recipientClient.setId(1L);
        Client senderClient = new Client("name", "surname", "patronymic", senderUser, new ClientStatus(ClientStatusEnumeration.ACTIVE.getId(), ClientStatusEnumeration.ACTIVE.name()));
        senderClient.setId(2L);

        Account recipientAccount = new Account(recipientClient, UUID.randomUUID(), new AccountType(AccountTypeEnumeration.DEBIT.getId(), AccountTypeEnumeration.DEBIT.name()), new AccountStatus(AccountStatusEnumeration.OPEN.getId(), AccountStatusEnumeration.OPEN.name()));
        recipientAccount.setBalance(10000.0);
        recipientAccount.setId(1L);
        Account senderAccount = new Account(senderClient, UUID.randomUUID(), new AccountType(AccountTypeEnumeration.DEBIT.getId(), AccountTypeEnumeration.DEBIT.name()), new AccountStatus(AccountStatusEnumeration.OPEN.getId(), AccountStatusEnumeration.OPEN.name()));
        senderAccount.setBalance(10000.0);
        senderAccount.setId(2L);

        transferTransaction = Transaction.builder()
                .setTransactionStatus(new TransactionStatus(TransactionStatusEnumeration.ACCEPTED.getId(), TransactionStatusEnumeration.ACCEPTED.name()))
                .setTransactionType(new TransactionType(TransactionTypeEnumeration.INSERT.getId(), TransactionTypeEnumeration.INSERT.name()))
                .setRecipient(recipientAccount)
                .setSender(senderAccount)
                .setTime(new Timestamp(1))
                .setAmount(1000)
                .build();
    }

    @Test
    public void insert_invalidTransaction_recipientUserIdNotEqualToContextUserId_throwsTransactionException(){
        User contextUser = new User("email", "password");
        contextUser.setId(3L);
        Mockito.when(userAuthenticationContextServiceManager.getCurrentAuthentication()).thenReturn(contextUser);
        Assertions.assertThrows(TransactionException.class, () -> debitAccountTransactionServiceManager.insert(insertTransaction));
    }

    @Test
    public void insert_validTransaction_success(){
        User contextUser = new User("email", "password");
        contextUser.setId(1L);
        Mockito.when(userAuthenticationContextServiceManager.getCurrentAuthentication()).thenReturn(contextUser);
        Mockito.when(accountRepository.existsAccountByAccountId(insertTransaction.getRecipient().getAccountId())).thenReturn(true);
        Mockito.when(accountEnvironment.getACCOUNT_TRANSACTION_MAX_AMOUNT()).thenReturn(1_000_000_000.0);
        Mockito.when(accountEnvironment.getACCOUNT_TRANSACTION_MIN_AMOUNT()).thenReturn(1.0);
        Mockito.when(accountEnvironment.getACCOUNT_BALANCE_MAX_AMOUNT()).thenReturn(1_000_000_000.0);
        Mockito.when(accountRepository.save(insertTransaction.getRecipient())).thenReturn(insertTransaction.getRecipient());
        Assertions.assertNull(debitAccountTransactionServiceManager.insert(insertTransaction));
    }

    @Test
    public void transfer_invalidTransaction_senderUserIdNotEqualToContextUserId_throwsTransactionException(){
        User contextUser = new User("email", "password");
        contextUser.setId(3L);
        Mockito.when(userAuthenticationContextServiceManager.getCurrentAuthentication()).thenReturn(contextUser);
        Assertions.assertThrows(TransactionException.class, () -> debitAccountTransactionServiceManager.transfer(transferTransaction));
    }

    @Test
    public void transfer_validTransaction_success(){
        User contextUser = new User("email", "password");
        contextUser.setId(2L);
        Mockito.when(userAuthenticationContextServiceManager.getCurrentAuthentication()).thenReturn(contextUser);
        Mockito.when(accountRepository.existsAccountByAccountId(transferTransaction.getSender().getAccountId())).thenReturn(true);
        Mockito.when(accountRepository.existsAccountByAccountId(transferTransaction.getRecipient().getAccountId())).thenReturn(true);
        Mockito.when(accountEnvironment.getACCOUNT_TRANSACTION_MAX_AMOUNT()).thenReturn(1_000_000_000.0);
        Mockito.when(accountEnvironment.getACCOUNT_TRANSACTION_MIN_AMOUNT()).thenReturn(1.0);
        Mockito.when(accountEnvironment.getACCOUNT_BALANCE_MAX_AMOUNT()).thenReturn(1_000_000_000.0);
        Mockito.when(accountRepository.saveAll(List.of(transferTransaction.getSender(), transferTransaction.getRecipient()))).thenReturn(null);
        Assertions.assertNull(debitAccountTransactionServiceManager.transfer(transferTransaction));
    }
}
