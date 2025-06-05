package com.example.transaction_service.service.transaction.kafka.listener.implementation;

import com.example.transaction_service.environment.kafka.KafkaEnvironment;
import com.example.transaction_service.model.account.type.enumeration.AccountTypeEnumeration;
import com.example.transaction_service.model.transaction.entity.Transaction;
import com.example.transaction_service.model.transaction.status.entity.TransactionStatus;
import com.example.transaction_service.model.transaction.status.enumeration.TransactionStatusEnumeration;
import com.example.transaction_service.model.transaction.type.enumeration.TransactionTypeEnumeration;
import com.example.transaction_service.service.kafka.producer.KafkaProducerService;
import com.example.transaction_service.service.transaction.AbstractAccountTransactionService;
import com.example.transaction_service.service.transaction.kafka.listener.KafkaListenerService;
import com.example.transaction_service.service.transaction.router.AccountTransactionServiceRouter;
import com.example.transaction_service.service.transaction.status.TransactionStatusService;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;


/**
 * Реализация {@link KafkaListenerService} для считывания сообщений, хранящих {@link Transaction}
 */
@Service
public class RequestTransactionKafkaListenerService implements KafkaListenerService<String, Transaction> {
    private final AccountTransactionServiceRouter<AbstractAccountTransactionService<Transaction>> accountTransactionServiceRouter;
    private final TransactionStatusService<TransactionStatus> transactionStatusService;
    private final KafkaProducerService<String, Transaction> kafkaProducerService;
    private final KafkaEnvironment kafkaEnvironment;

    public RequestTransactionKafkaListenerService(@Qualifier("accountTransactionServiceRouterManager") AccountTransactionServiceRouter<AbstractAccountTransactionService<Transaction>> accountTransactionServiceRouter, @Qualifier("transactionStatusServiceManager") TransactionStatusService<TransactionStatus> transactionStatusService, @Qualifier("transactionKafkaProducerService") KafkaProducerService<String, Transaction> kafkaProducerService, KafkaEnvironment kafkaEnvironment) {
        this.accountTransactionServiceRouter = accountTransactionServiceRouter;
        this.transactionStatusService = transactionStatusService;
        this.kafkaProducerService = kafkaProducerService;
        this.kafkaEnvironment = kafkaEnvironment;
    }

    @Override
    @KafkaListener(topics = "${kafka.topic.transaction.name}", groupId = "${kafka.topic.transaction.transaction-id}", containerFactory = "transactionListenerContainerFactory")
    public void listen(ConsumerRecord<String, Transaction> listenableObject) {
        Transaction transaction = listenableObject.value();
        AbstractAccountTransactionService<Transaction> transactionService = accountTransactionServiceRouter.getByAccountTypeEnumeration(AccountTypeEnumeration.getById(transaction.getSender().getAccountType().getId()));
        try {
            if (transaction.getTransactionType().getId().equals(TransactionTypeEnumeration.INSERT.getId())) {
                kafkaProducerService.send(new ProducerRecord<>(kafkaEnvironment.getKAFKA_TOPIC_TRANSACTION_ACCEPT(), TransactionTypeEnumeration.INSERT.toString(), transactionService.insert(transaction)));

            } else if (transaction.getTransactionType().getId().equals(TransactionTypeEnumeration.TRANSFER.getId())) {
                kafkaProducerService.send(new ProducerRecord<>(kafkaEnvironment.getKAFKA_TOPIC_TRANSACTION_ACCEPT(), TransactionTypeEnumeration.TRANSFER.toString(), transactionService.transfer(transaction)));
            }
        } catch (Exception e){
            transaction.setTransactionStatus(transactionStatusService.getById(TransactionStatusEnumeration.REJECTED.getId()));
            if (transaction.getTransactionType().getId().equals(TransactionTypeEnumeration.INSERT.getId())) {
                kafkaProducerService.send(new ProducerRecord<>(kafkaEnvironment.getKAFKA_TOPIC_TRANSACTION_RESULT(), TransactionTypeEnumeration.INSERT.toString(), transaction));
            } else if (transaction.getTransactionType().getId().equals(TransactionTypeEnumeration.TRANSFER.getId())) {
                kafkaProducerService.send(new ProducerRecord<>(kafkaEnvironment.getKAFKA_TOPIC_TRANSACTION_RESULT(), TransactionTypeEnumeration.TRANSFER.toString(), transaction));
            }
        }
    }
}
