package com.example.transaction_manager_service.service.transaction.kafka.listener.implementation;

import com.example.transaction_manager_service.environment.kafka.KafkaEnvironment;
import com.example.transaction_manager_service.model.transaction.entity.Transaction;
import com.example.transaction_manager_service.service.transaction.kafka.listener.KafkaListenerService;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;


/**
 * Реализация {@link KafkaListenerService} для считывания сообщений, хранящих {@link Transaction}
 */
@Service
public class TransactionKafkaListenerService implements KafkaListenerService<String, Transaction> {
    private final KafkaEnvironment kafkaEnvironment;

    public TransactionKafkaListenerService(KafkaEnvironment kafkaEnvironment) {
        this.kafkaEnvironment = kafkaEnvironment;
    }

    @Override
    @KafkaListener(topics = "${kafka.topic.transaction-accept.name}", groupId = "${kafka.topic.transaction-accept.transaction-id}", containerFactory = "transactionListenerContainerFactory")
    public void listen(ConsumerRecord<String, Transaction> listenableObject) {
        System.out.println(listenableObject.value().toString());
    }
}
