package com.example.transaction_manager_service.service.common.kafka.producer.implementation;

import com.example.transaction_manager_service.model.transaction.entity.Transaction;
import com.example.transaction_manager_service.service.common.kafka.producer.KafkaProducerService;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TransactionKafkaProducerService implements KafkaProducerService<String, Transaction> {
    private final KafkaTemplate<String, Transaction> kafkaTemplate;

    public TransactionKafkaProducerService(KafkaTemplate<String, Transaction> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    @Transactional("kafkaTransactionTransactionManager")
    public void send(ProducerRecord<String, Transaction> producerRecord) {
        kafkaTemplate.send(producerRecord);
    }
}
