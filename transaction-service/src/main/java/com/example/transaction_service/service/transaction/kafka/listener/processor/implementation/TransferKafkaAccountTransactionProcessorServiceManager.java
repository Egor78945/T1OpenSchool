package com.example.transaction_service.service.transaction.kafka.listener.processor.implementation;

import com.example.transaction_service.environment.web.WebClientEnvironment;
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
import com.example.transaction_service.model.transaction.status.enumeration.TransactionStatusEnumeration;
import com.example.transaction_service.model.transaction.type.enumeration.TransactionTypeEnumeration;
import com.example.transaction_service.service.account.AbstractAccountService;
import com.example.transaction_service.service.account.router.AccountServiceRouter;
import com.example.transaction_service.service.account.status.AccountStatusService;
import com.example.transaction_service.service.client.AbstractClientService;
import com.example.transaction_service.service.client.status.ClientStatusService;
import com.example.transaction_service.service.common.client.WebClientService;
import com.example.transaction_service.service.common.client.builder.WebClientBuilder;
import com.example.transaction_service.service.common.kafka.producer.KafkaProducerService;
import com.example.transaction_service.service.transaction.AbstractAccountTransactionService;
import com.example.transaction_service.service.transaction.kafka.listener.processor.AbstractKafkaAccountTransactionProcessorService;
import com.example.transaction_service.service.transaction.router.AccountTransactionServiceRouter;
import com.example.transaction_service.service.transaction.status.TransactionStatusService;
import org.apache.hc.core5.http.ClassicHttpRequest;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.temporal.ChronoUnit;
import java.util.Map;

@Service
public class TransferKafkaAccountTransactionProcessorServiceManager extends AbstractKafkaAccountTransactionProcessorService<String, Transaction> {
    public TransferKafkaAccountTransactionProcessorServiceManager(@Qualifier("accountTransactionServiceRouterManager") AccountTransactionServiceRouter<AbstractAccountTransactionService<Transaction>> accountTransactionServiceRouter, @Qualifier("accountServiceRouterManager") AccountServiceRouter<AbstractAccountService<Account>> accountServiceRouter, @Qualifier("transactionStatusServiceManager") TransactionStatusService<TransactionStatus> transactionStatusService, @Qualifier("transactionKafkaProducerService") KafkaProducerService<String, Transaction> kafkaProducerService, @Qualifier("webClientServiceManager") WebClientService<ClassicHttpRequest, String> webClientService, @Qualifier("clientServiceManager") AbstractClientService<Client> clientService, @Qualifier("clientStatusServiceManager") ClientStatusService<ClientStatus> clientStatusService, @Qualifier("accountStatusServiceManager") AccountStatusService<AccountStatus> accountStatusService, WebClientEnvironment webClientEnvironment, KafkaEnvironment kafkaEnvironment) {
        super(accountTransactionServiceRouter, accountServiceRouter, transactionStatusService, kafkaProducerService, webClientService, clientService, clientStatusService, accountStatusService, webClientEnvironment, kafkaEnvironment);
    }

    @Override
    public void process(ConsumerRecord<String, Transaction> listenableObject) {
        Transaction transaction = listenableObject.value();
        AbstractAccountTransactionService<Transaction> transactionService = accountTransactionServiceRouter.getByAccountTypeEnumeration(AccountTypeEnumeration.getById(transaction.getSender().getAccountType().getId()));

        if (!checkClientPermission(transaction)) {
            return;
        }
        try {
            transaction = transactionService.transfer(transaction);
            kafkaProducerService.send(new ProducerRecord<>(kafkaEnvironment.getKAFKA_TOPIC_TRANSACTION_ACCEPT(), TransactionTypeEnumeration.TRANSFER.toString(), transaction));
        } catch (Exception e) {
            reject(transaction);
        }
    }

    @Override
    protected boolean checkClientPermission(Transaction transaction) {
        AbstractAccountTransactionService<Transaction> transactionService = accountTransactionServiceRouter.getByAccountTypeEnumeration(AccountTypeEnumeration.getById(transaction.getSender().getAccountType().getId()));
        AbstractAccountService<Account> accountService = accountServiceRouter.getByAccountTypeEnumeration(AccountTypeEnumeration.getById(transaction.getSender().getAccountType().getId()));
        if (transaction.getSender().getClient().getClient_status_id() == null) {
            try {
                String httpEntity = webClientService.sendRequest(WebClientBuilder.build(String.format("http://%s:%s/api/v1/client/blocked", webClientEnvironment.getTRANSACTION_MANAGER_SERVICE_HOST(), webClientEnvironment.getTRANSACTION_MANAGER_SERVICE_PORT_INNER()), Map.of("clientId", transaction.getSender().getClient().getClient_id().toString(), "accountId", transaction.getSender().getAccountId().toString())));
                boolean clientIsInBlackList = Boolean.parseBoolean(httpEntity);
                if (clientIsInBlackList) {
                    Client senderClient = transaction.getSender().getClient();
                    senderClient.setClient_status_id(clientStatusService.getById(ClientStatusEnumeration.BLOCKED.getId()));
                    clientService.update(senderClient);

                    if (transactionService.getByRecipientAfterTime(transaction.getSender().getAccountId(), Timestamp.from(transaction.getTime().toInstant().minus(10, ChronoUnit.MINUTES))) > 11) {
                        Account senderAccount = transaction.getSender();
                        senderAccount.setAccountStatus(accountStatusService.getById(AccountStatusEnumeration.ARRESTED.getId()));
                        accountService.update(senderAccount);
                    }
                    reject(transaction);
                    return false;
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return true;
    }

    @Override
    protected void reject(Transaction transaction) {
        transaction.setTransactionStatus(transactionStatusService.getById(TransactionStatusEnumeration.REJECTED.getId()));
        accountTransactionServiceRouter.getByAccountTypeEnumeration(AccountTypeEnumeration.DEBIT).update(transaction);
    }
}
