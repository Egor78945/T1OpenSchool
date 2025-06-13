package com.example.transaction_service.service.transaction.kafka.listener.implementation;

import com.example.transaction_service.model.account.type.enumeration.AccountTypeEnumeration;
import com.example.transaction_service.model.transaction.entity.Transaction;
import com.example.transaction_service.model.transaction.status.entity.TransactionStatus;
import com.example.transaction_service.model.transaction.status.enumeration.TransactionStatusEnumeration;
import com.example.transaction_service.model.transaction.type.enumeration.TransactionTypeEnumeration;
import com.example.transaction_service.service.transaction.AbstractAccountTransactionService;
import com.example.transaction_service.service.transaction.kafka.listener.KafkaListenerService;
import com.example.transaction_service.service.transaction.kafka.listener.processor.router.AbstractKafkaAccountTransactionProcessorServiceRouter;
import com.example.transaction_service.service.transaction.router.AccountTransactionServiceRouter;
import com.example.transaction_service.service.transaction.status.TransactionStatusService;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;


/**git
 * Реализация {@link KafkaListenerService} для считывания сообщений, хранящих {@link Transaction} из топика <b>TRANSACTION</b>
 */
@Service
public class RequestTransactionKafkaListenerService implements KafkaListenerService<String, Transaction> {
    private final AccountTransactionServiceRouter<AbstractAccountTransactionService<Transaction>> accountTransactionServiceRouter;
    private final TransactionStatusService<TransactionStatus> transactionStatusService;
    private final AbstractKafkaAccountTransactionProcessorServiceRouter<String, Transaction> kafkaAccountTransactionProcessorServiceRouter;

    public RequestTransactionKafkaListenerService(@Qualifier("accountTransactionServiceRouterManager") AccountTransactionServiceRouter<AbstractAccountTransactionService<Transaction>> accountTransactionServiceRouter, @Qualifier("transactionStatusServiceManager") TransactionStatusService<TransactionStatus> transactionStatusService, @Qualifier("kafkaAccountTransactionProcessorServiceRouterManager") AbstractKafkaAccountTransactionProcessorServiceRouter<String, Transaction> kafkaAccountTransactionProcessorServiceRouter) {
        this.accountTransactionServiceRouter = accountTransactionServiceRouter;
        this.transactionStatusService = transactionStatusService;
        this.kafkaAccountTransactionProcessorServiceRouter = kafkaAccountTransactionProcessorServiceRouter;
    }

    @Override
    @KafkaListener(topics = "${kafka.topic.transaction.name}", groupId = "${kafka.topic.transaction.transaction-id}", containerFactory = "transactionListenerContainerFactory")
    public void listen(ConsumerRecord<String, Transaction> listenableObject) {
        Transaction transaction = listenableObject.value();
        try {
            kafkaAccountTransactionProcessorServiceRouter.getBy(TransactionTypeEnumeration.getById(transaction.getTransactionType().getId())).process(listenableObject);
        } catch (Exception e){
            transaction.setTransactionStatus(transactionStatusService.getById(TransactionStatusEnumeration.REJECTED.getId()));
            accountTransactionServiceRouter.getByAccountTypeEnumeration(AccountTypeEnumeration.DEBIT).save(transaction);
        }
    }
}