package com.example.transaction_service.service.transaction.kafka.listener.processor.implementation;

import com.example.transaction_service.environment.client.WebClientEnvironment;
import com.example.transaction_service.environment.kafka.KafkaEnvironment;
import com.example.transaction_service.exception.TransactionException;
import com.example.transaction_service.model.account.entity.Account;
import com.example.transaction_service.model.account.status.entity.AccountStatus;
import com.example.transaction_service.model.account.type.enumeration.AccountTypeEnumeration;
import com.example.transaction_service.model.client.entity.Client;
import com.example.transaction_service.model.client.status.entity.ClientStatus;
import com.example.transaction_service.model.transaction.entity.Transaction;
import com.example.transaction_service.model.transaction.status.entity.TransactionStatus;
import com.example.transaction_service.model.transaction.status.enumeration.TransactionStatusEnumeration;
import com.example.transaction_service.model.transaction.type.enumeration.TransactionTypeEnumeration;
import com.example.transaction_service.service.account.AbstractAccountService;
import com.example.transaction_service.service.account.router.AccountServiceRouter;
import com.example.transaction_service.service.account.status.AccountStatusService;
import com.example.transaction_service.service.client.AbstractClientService;
import com.example.transaction_service.service.client.status.ClientStatusService;
import com.example.transaction_service.service.common.client.WebClientService;
import com.example.transaction_service.service.common.kafka.producer.KafkaProducerService;
import com.example.transaction_service.service.transaction.AbstractAccountTransactionService;
import com.example.transaction_service.service.transaction.kafka.listener.processor.AbstractKafkaAccountTransactionProcessorService;
import com.example.transaction_service.service.transaction.router.AccountTransactionServiceRouter;
import com.example.transaction_service.service.transaction.status.TransactionStatusService;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.core5.http.ClassicHttpRequest;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class TransferKafkaAccountTransactionProcessorServiceManager extends AbstractKafkaAccountTransactionProcessorService<String, Transaction> {
    public TransferKafkaAccountTransactionProcessorServiceManager(@Qualifier("accountTransactionServiceRouterManager") AccountTransactionServiceRouter<AbstractAccountTransactionService<Transaction>> accountTransactionServiceRouter, @Qualifier("accountServiceRouterManager") AccountServiceRouter<AbstractAccountService<Account>> accountServiceRouter, @Qualifier("transactionStatusServiceManager") TransactionStatusService<TransactionStatus> transactionStatusService, @Qualifier("transactionKafkaProducerService") KafkaProducerService<String, Transaction> kafkaProducerService, @Qualifier("webClientServiceManager") WebClientService<ClassicHttpRequest, CloseableHttpResponse> webClientService, @Qualifier("clientServiceManager") AbstractClientService<Client> clientService, @Qualifier("clientStatusServiceManager") ClientStatusService<ClientStatus> clientStatusService, @Qualifier("accountStatusServiceManager") AccountStatusService<AccountStatus> accountStatusService, WebClientEnvironment webClientEnvironment, KafkaEnvironment kafkaEnvironment) {
        super(accountTransactionServiceRouter, accountServiceRouter, transactionStatusService, kafkaProducerService, webClientService, clientService, clientStatusService, accountStatusService, webClientEnvironment, kafkaEnvironment);
    }

    @Override
    public void process(ConsumerRecord<String, Transaction> listenableObject) {
        Transaction transaction = listenableObject.value();
        AbstractAccountTransactionService<Transaction> transactionService = accountTransactionServiceRouter.getByAccountTypeEnumeration(AccountTypeEnumeration.getById(transaction.getSender().getAccountType().getId()));

        checkClientPermission(transaction);
        try {
            kafkaProducerService.send(new ProducerRecord<>(kafkaEnvironment.getKAFKA_TOPIC_TRANSACTION_ACCEPT(), TransactionTypeEnumeration.TRANSFER.toString(), transactionService.insert(transaction)));
        } catch (TransactionException e) {
            reject(transaction);
        }
    }

    @Override
    protected void reject(Transaction transaction) {
        transaction.setTransactionStatus(transactionStatusService.getById(TransactionStatusEnumeration.REJECTED.getId()));
        accountTransactionServiceRouter.getByAccountTypeEnumeration(AccountTypeEnumeration.DEBIT).save(transaction);
    }
}
