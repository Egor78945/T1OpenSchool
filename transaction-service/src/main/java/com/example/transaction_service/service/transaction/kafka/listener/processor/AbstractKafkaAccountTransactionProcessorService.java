package com.example.transaction_service.service.transaction.kafka.listener.processor;

import com.example.transaction_service.environment.web.WebClientEnvironment;
import com.example.transaction_service.environment.kafka.KafkaEnvironment;
import com.example.transaction_service.model.account.entity.Account;
import com.example.transaction_service.model.account.status.entity.AccountStatus;
import com.example.transaction_service.model.client.entity.Client;
import com.example.transaction_service.model.client.status.entity.ClientStatus;
import com.example.transaction_service.model.transaction.entity.Transaction;
import com.example.transaction_service.model.transaction.status.entity.TransactionStatus;
import com.example.transaction_service.service.account.AbstractAccountService;
import com.example.transaction_service.service.account.router.AccountServiceRouter;
import com.example.transaction_service.service.account.status.AccountStatusService;
import com.example.transaction_service.service.client.AbstractClientService;
import com.example.transaction_service.service.client.status.ClientStatusService;
import com.example.transaction_service.service.common.client.WebClientService;
import com.example.transaction_service.service.common.kafka.producer.KafkaProducerService;
import com.example.transaction_service.service.transaction.AbstractAccountTransactionService;
import com.example.transaction_service.service.transaction.router.AccountTransactionServiceRouter;
import com.example.transaction_service.service.transaction.status.TransactionStatusService;
import org.apache.hc.core5.http.ClassicHttpRequest;
import org.apache.kafka.clients.consumer.ConsumerRecord;

import java.io.Serializable;

public abstract class AbstractKafkaAccountTransactionProcessorService<K extends Serializable, V extends Transaction> {
    protected final AccountTransactionServiceRouter<AbstractAccountTransactionService<Transaction>> accountTransactionServiceRouter;
    protected final AccountServiceRouter<AbstractAccountService<Account>> accountServiceRouter;
    protected final TransactionStatusService<TransactionStatus> transactionStatusService;
    protected final KafkaProducerService<String, Transaction> kafkaProducerService;
    protected final WebClientService<ClassicHttpRequest, String> webClientService;
    protected final AbstractClientService<Client> clientService;
    protected final ClientStatusService<ClientStatus> clientStatusService;
    protected final AccountStatusService<AccountStatus> accountStatusService;
    protected final WebClientEnvironment webClientEnvironment;
    protected final KafkaEnvironment kafkaEnvironment;

    public AbstractKafkaAccountTransactionProcessorService(AccountTransactionServiceRouter<AbstractAccountTransactionService<Transaction>> accountTransactionServiceRouter, AccountServiceRouter<AbstractAccountService<Account>> accountServiceRouter, TransactionStatusService<TransactionStatus> transactionStatusService, KafkaProducerService<String, Transaction> kafkaProducerService, WebClientService<ClassicHttpRequest, String> webClientService, AbstractClientService<Client> clientService, ClientStatusService<ClientStatus> clientStatusService, AccountStatusService<AccountStatus> accountStatusService, WebClientEnvironment webClientEnvironment, KafkaEnvironment kafkaEnvironment) {
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


    protected abstract boolean checkClientPermission(V transaction);

    protected abstract void reject(V transaction);
}
