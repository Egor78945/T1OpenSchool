package com.example.transaction_service.service.kafka.producer.implementation;

import com.example.transaction_service.model.transaction.entity.Transaction;
import com.example.transaction_service.service.kafka.producer.KafkaProducerService;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class TransactionKafkaProducerService implements KafkaProducerService<String, Transaction> {
    private final KafkaTemplate<String, Transaction> kafkaTemplate;

    public TransactionKafkaProducerService(KafkaTemplate<String, Transaction> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public void send(ProducerRecord<String, Transaction> producerRecord) {
        kafkaTemplate.send(producerRecord);
    }
}
