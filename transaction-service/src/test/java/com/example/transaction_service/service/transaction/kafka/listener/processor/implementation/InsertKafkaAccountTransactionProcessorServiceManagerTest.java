package com.example.transaction_service.service.transaction.kafka.listener.processor.implementation;

import com.example.transaction_service.environment.kafka.KafkaEnvironment;
import com.example.transaction_service.environment.web.WebClientEnvironment;
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
import com.example.transaction_service.service.account.AbstractAccountService;
import com.example.transaction_service.service.account.implementation.CreditAccountServiceManager;
import com.example.transaction_service.service.account.router.AccountServiceRouter;
import com.example.transaction_service.service.account.status.AccountStatusService;
import com.example.transaction_service.service.client.AbstractClientService;
import com.example.transaction_service.service.client.status.ClientStatusService;
import com.example.transaction_service.service.common.client.WebClientService;
import com.example.transaction_service.service.common.kafka.producer.KafkaProducerService;
import com.example.transaction_service.service.transaction.AbstractAccountTransactionService;
import com.example.transaction_service.service.transaction.implementation.CreditAccountTransactionServiceManager;
import com.example.transaction_service.service.transaction.router.AccountTransactionServiceRouter;
import com.example.transaction_service.service.transaction.status.TransactionStatusService;
import org.apache.hc.core5.http.ClassicHttpRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
public class InsertKafkaAccountTransactionProcessorServiceManagerTest {
    @Mock
    private AccountTransactionServiceRouter<AbstractAccountTransactionService<Transaction>> accountTransactionServiceRouter;
    @Mock
    private AccountServiceRouter<AbstractAccountService<Account>> accountServiceRouter;
    @Mock
    private WebClientService<ClassicHttpRequest, String> webClientService;
    @Mock
    private AbstractClientService<Client> clientService;
    @Mock
    private ClientStatusService<ClientStatus> clientStatusService;
    @Mock
    private WebClientEnvironment webClientEnvironment;
    @Mock
    private TransactionRepository transactionRepository;
    @InjectMocks
    @Spy
    private InsertKafkaAccountTransactionProcessorServiceManager insertKafkaAccountTransactionProcessorServiceManager;
    private static Transaction insertTransactionWithNullClientStatus;
    private static Transaction insertTransactionWithNotNullClientStatus;

    @BeforeAll
    public static void initInsertTransactionWithNullClientStatus() {
        User recipientUser = new User("email", "password");
        recipientUser.setId(1L);

        Client recipientClient = new Client("name", "surname", "patronymic", recipientUser, null);
        recipientClient.setClient_id(UUID.randomUUID());
        recipientClient.setId(1L);

        Account recipientAccount = new Account(recipientClient, UUID.randomUUID(), new AccountType(AccountTypeEnumeration.CREDIT.getId(), AccountTypeEnumeration.CREDIT.name()), new AccountStatus(AccountStatusEnumeration.OPEN.getId(), AccountStatusEnumeration.OPEN.name()));
        recipientAccount.setId(1L);

        insertTransactionWithNullClientStatus = Transaction.builder()
                .setTransactionStatus(new TransactionStatus(TransactionStatusEnumeration.ACCEPTED.getId(), TransactionStatusEnumeration.ACCEPTED.name()))
                .setTransactionType(new TransactionType(TransactionTypeEnumeration.INSERT.getId(), TransactionTypeEnumeration.INSERT.name()))
                .setRecipient(recipientAccount)
                .setTime(new Timestamp(1))
                .setAmount(1000)
                .build();
        insertTransactionWithNullClientStatus.setTransaction_id(UUID.randomUUID());
        insertTransactionWithNullClientStatus.setId(1L);
    }

    @BeforeAll
    public static void initInsertTransactionWithNotNullClientStatus() {
        User recipientUser = new User("email", "password");
        recipientUser.setId(2L);


        Client recipientClient = new Client("name", "surname", "patronymic", recipientUser, new ClientStatus(ClientStatusEnumeration.ACTIVE.getId(), ClientStatusEnumeration.ACTIVE.name()));
        recipientClient.setClient_id(UUID.randomUUID());
        recipientClient.setId(2L);

        Account recipientAccount = new Account(recipientClient, UUID.randomUUID(), new AccountType(AccountTypeEnumeration.CREDIT.getId(), AccountTypeEnumeration.CREDIT.name()), new AccountStatus(AccountStatusEnumeration.OPEN.getId(), AccountStatusEnumeration.OPEN.name()));
        recipientAccount.setId(2L);
        insertTransactionWithNotNullClientStatus = Transaction.builder()
                .setTransactionStatus(new TransactionStatus(TransactionStatusEnumeration.ACCEPTED.getId(), TransactionStatusEnumeration.ACCEPTED.name()))
                .setTransactionType(new TransactionType(TransactionTypeEnumeration.INSERT.getId(), TransactionTypeEnumeration.INSERT.name()))
                .setRecipient(recipientAccount)
                .setTime(new Timestamp(1))
                .setAmount(1000)
                .build();
        insertTransactionWithNotNullClientStatus.setId(2L);
    }

    @Test
    public void checkClientPermission_recipientClientStatusIdIsNotNull_returnTrue() {
        Mockito.when(accountTransactionServiceRouter.getByAccountTypeEnumeration(AccountTypeEnumeration.CREDIT)).thenReturn(new CreditAccountTransactionServiceManager(null, null, null, null, null, null));
        Mockito.when(accountServiceRouter.getByAccountTypeEnumeration(Mockito.any())).thenReturn(Mockito.any());
        Assertions.assertTrue(insertKafkaAccountTransactionProcessorServiceManager.checkClientPermission(insertTransactionWithNotNullClientStatus));
    }

    @Test
    public void checkClientPermission_recipientClientStatusIdIsNull_and_clientIsNotInBlackList_returnTrue(){
        Mockito.when(accountTransactionServiceRouter.getByAccountTypeEnumeration(AccountTypeEnumeration.CREDIT)).thenReturn(new CreditAccountTransactionServiceManager(null, null, null, null, null, null));
        Mockito.when(accountServiceRouter.getByAccountTypeEnumeration(AccountTypeEnumeration.CREDIT)).thenReturn(new CreditAccountServiceManager(null, null, null, null, null));
        try {
            Mockito.when(webClientService.sendRequest(Mockito.any())).thenReturn("false");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Assertions.assertTrue(insertKafkaAccountTransactionProcessorServiceManager.checkClientPermission(insertTransactionWithNullClientStatus));

    }

    @Test
    public void checkClientPermission_recipientClientStatusIdIsNull_and_clientIsInBlackList_and_thereIsNotTransactionsAfterTime_returnFalse(){
        Mockito.when(accountTransactionServiceRouter.getByAccountTypeEnumeration(AccountTypeEnumeration.CREDIT)).thenReturn(new CreditAccountTransactionServiceManager(null, transactionRepository, null, null, null, null));
        Mockito.when(accountServiceRouter.getByAccountTypeEnumeration(AccountTypeEnumeration.CREDIT)).thenReturn(new CreditAccountServiceManager(null, null, null, null, null));
        try {
            Mockito.when(webClientService.sendRequest(Mockito.any())).thenReturn("true");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Mockito.when(clientStatusService.getById(ClientStatusEnumeration.BLOCKED.getId())).thenReturn(new ClientStatus());
        Mockito.when(clientService.update(Mockito.any())).thenReturn(null);
        Mockito.when(transactionRepository.countRejectedTransactionsByRecipientPerTimeByAccountId(Mockito.any(), Mockito.any())).thenReturn(0);
        Mockito.doNothing().when(insertKafkaAccountTransactionProcessorServiceManager).reject(Mockito.any());
        Assertions.assertFalse(insertKafkaAccountTransactionProcessorServiceManager.checkClientPermission(insertTransactionWithNullClientStatus));
    }
}
