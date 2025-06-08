package com.example.transaction_service.service.transaction.kafka.listener.processor;

import com.example.transaction_service.environment.client.WebClientEnvironment;
import com.example.transaction_service.environment.kafka.KafkaEnvironment;
import com.example.transaction_service.model.account.entity.Account;
import com.example.transaction_service.model.account.status.entity.AccountStatus;
import com.example.transaction_service.model.account.status.entity.enumeration.AccountStatusEnumeration;
import com.example.transaction_service.model.account.type.enumeration.AccountTypeEnumeration;
import com.example.transaction_service.model.client.entity.Client;
import com.example.transaction_service.model.client.status.entity.ClientStatus;
import com.example.transaction_service.model.client.status.enumeration.ClientStatusEnumeration;
import com.example.transaction_service.model.transaction.entity.Transaction;
import com.example.transaction_service.model.transaction.status.entity.TransactionStatus;
import com.example.transaction_service.service.account.AbstractAccountService;
import com.example.transaction_service.service.account.router.AccountServiceRouter;
import com.example.transaction_service.service.account.status.AccountStatusService;
import com.example.transaction_service.service.client.AbstractClientService;
import com.example.transaction_service.service.client.status.ClientStatusService;
import com.example.transaction_service.service.common.client.WebClientService;
import com.example.transaction_service.service.common.client.builder.WebClientBuilder;
import com.example.transaction_service.service.common.kafka.producer.KafkaProducerService;
import com.example.transaction_service.service.transaction.AbstractAccountTransactionService;
import com.example.transaction_service.service.transaction.router.AccountTransactionServiceRouter;
import com.example.transaction_service.service.transaction.status.TransactionStatusService;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.core5.http.ClassicHttpRequest;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.kafka.clients.consumer.ConsumerRecord;

import java.io.IOException;
import java.io.Serializable;
import java.sql.Timestamp;
import java.time.temporal.ChronoUnit;
import java.util.Map;

public abstract class AbstractKafkaAccountTransactionProcessorService<K extends Serializable, V extends Transaction> {
    protected final AccountTransactionServiceRouter<AbstractAccountTransactionService<Transaction>> accountTransactionServiceRouter;
    protected final AccountServiceRouter<AbstractAccountService<Account>> accountServiceRouter;
    protected final TransactionStatusService<TransactionStatus> transactionStatusService;
    protected final KafkaProducerService<String, Transaction> kafkaProducerService;
    protected final WebClientService<ClassicHttpRequest, CloseableHttpResponse> webClientService;
    protected final AbstractClientService<Client> clientService;
    protected final ClientStatusService<ClientStatus> clientStatusService;
    protected final AccountStatusService<AccountStatus> accountStatusService;
    protected final WebClientEnvironment webClientEnvironment;
    protected final KafkaEnvironment kafkaEnvironment;

    public AbstractKafkaAccountTransactionProcessorService(AccountTransactionServiceRouter<AbstractAccountTransactionService<Transaction>> accountTransactionServiceRouter, AccountServiceRouter<AbstractAccountService<Account>> accountServiceRouter, TransactionStatusService<TransactionStatus> transactionStatusService, KafkaProducerService<String, Transaction> kafkaProducerService, WebClientService<ClassicHttpRequest, CloseableHttpResponse> webClientService, AbstractClientService<Client> clientService, ClientStatusService<ClientStatus> clientStatusService, AccountStatusService<AccountStatus> accountStatusService, WebClientEnvironment webClientEnvironment, KafkaEnvironment kafkaEnvironment) {
        this.accountTransactionServiceRouter = accountTransactionServiceRouter;
        this.accountServiceRouter = accountServiceRouter;
        this.transactionStatusService = transactionStatusService;
        this.kafkaProducerService = kafkaProducerService;
        this.webClientService = webClientService;
        this.clientService = clientService;
        this.clientStatusService = clientStatusService;
        this.accountStatusService = accountStatusService;
        this.webClientEnvironment = webClientEnvironment;
        this.kafkaEnvironment = kafkaEnvironment;
    }

    public abstract void process(ConsumerRecord<K, V> listenableObject);


    protected void checkClientPermission(V transaction) {
        AbstractAccountTransactionService<Transaction> transactionService = accountTransactionServiceRouter.getByAccountTypeEnumeration(AccountTypeEnumeration.getById(transaction.getSender().getAccountType().getId()));
        AbstractAccountService<Account> accountService = accountServiceRouter.getByAccountTypeEnumeration(AccountTypeEnumeration.getById(transaction.getRecipient().getAccountType().getId()));
        if (transaction.getRecipient().getClient().getClient_status_id() == null) {
            try (CloseableHttpResponse closeableHttpResponse = webClientService.sendRequest(WebClientBuilder.build(String.format("http://%s:%s/api/v1/client/blocked", webClientEnvironment.getTRANSACTION_MANAGER_SERVICE_HOST(), webClientEnvironment.getTRANSACTION_MANAGER_SERVICE_PORT_INNER()), Map.of("clientId", transaction.getRecipient().getClient().getClient_id().toString(), "accountId", transaction.getRecipient().getAccountId().toString())))) {
                boolean clientIsInBlackList = Boolean.parseBoolean(EntityUtils.toString(closeableHttpResponse.getEntity()));
                if (clientIsInBlackList) {
                    Client recipientClient = transaction.getRecipient().getClient();
                    recipientClient.setClient_status_id(clientStatusService.getById(ClientStatusEnumeration.BLOCKED.getId()));
                    clientService.update(recipientClient);
                    reject(transaction);
                    if (transactionService.getByRecipientAfterTime(transaction.getRecipient().getAccountId(), Timestamp.from(transaction.getTime().toInstant().minus(10, ChronoUnit.MINUTES))) > 11) {
                        Account recipientAccount = transaction.getRecipient();
                        recipientAccount.setAccountStatus(accountStatusService.getById(AccountStatusEnumeration.ARRESTED.getId()));
                        accountService.update(recipientAccount);
                    }
                }
            } catch (IOException | ParseException e) {
                throw new RuntimeException(e);
            }
        }
    }

    protected abstract void reject(V transaction);
}
