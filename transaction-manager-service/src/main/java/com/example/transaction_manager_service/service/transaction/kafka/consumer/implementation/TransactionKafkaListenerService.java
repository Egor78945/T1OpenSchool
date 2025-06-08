package com.example.transaction_manager_service.service.transaction.kafka.consumer.implementation;

import com.example.transaction_manager_service.model.transaction.entity.Transaction;
import com.example.transaction_manager_service.service.transaction.TransactionProcessorService;
import com.example.transaction_manager_service.service.common.kafka.consumer.KafkaListenerService;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;


/**
 * Реализация {@link KafkaListenerService} для считывания сообщений, хранящих {@link Transaction}
 */
@Service
public class TransactionKafkaListenerService implements KafkaListenerService<String, Transaction> {
    private final TransactionProcessorService<Transaction> transactionProcessorService;

    public TransactionKafkaListenerService(@Qualifier("transactionProcessorServiceManager") TransactionProcessorService<Transaction> transactionProcessorService) {
        this.transactionProcessorService = transactionProcessorService;
    }

    @Override
    @KafkaListener(topics = "${kafka.topic.transaction-accept.name}", groupId = "${kafka.topic.transaction-accept.transaction-id}", containerFactory = "transactionListenerContainerFactory")
    public void listen(ConsumerRecord<String, Transaction> listenableObject) {
        transactionProcessorService.accept(listenableObject.value());
    }
}
