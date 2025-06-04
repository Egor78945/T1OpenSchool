package com.example.transaction_service.service.transaction.kafka.listener.implementation;

import com.example.transaction_service.model.transaction.entity.Transaction;
import com.example.transaction_service.service.transaction.kafka.listener.KafkaListenerService;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class ResultTransactionKafkaListenerService implements KafkaListenerService<String, Transaction> {
    @Override
    @KafkaListener(topics = "${kafka.topic.transaction-result.name}", groupId = "${kafka.topic.transaction-result.transaction-id}", containerFactory = "transactionListenerContainerFactory")
    public void listen(ConsumerRecord<String, Transaction> listenableObject) {

    }
}
