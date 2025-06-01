package com.example.transaction_service.service.transaction.kafka.implementation;

import com.example.transaction_service.environment.kafka.KafkaEnvironment;
import com.example.transaction_service.model.transaction.entity.Transaction;
import com.example.transaction_service.service.kafka.producer.KafkaProducerService;
import com.example.transaction_service.service.transaction.kafka.AbstractKafkaAccountTransactionService;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * Реализация {@link AbstractKafkaAccountTransactionService}
 */
@Service
public class KafkaAccountTransactionServiceManager extends AbstractKafkaAccountTransactionService<String, Transaction> {
    public KafkaAccountTransactionServiceManager(KafkaEnvironment kafkaEnvironment, @Qualifier("transactionKafkaProducerService") KafkaProducerService<String, Transaction> kafkaProducerService) {
        super(kafkaEnvironment, kafkaProducerService);
    }

    @Override
    public void save(String key, Transaction transaction) {
        kafkaProducerService.send(new ProducerRecord<>(kafkaEnvironment.getKAFKA_TOPIC_TRANSACTION(), key, transaction));
    }
}