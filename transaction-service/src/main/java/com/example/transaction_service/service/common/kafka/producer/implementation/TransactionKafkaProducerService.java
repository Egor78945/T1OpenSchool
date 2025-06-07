package com.example.transaction_service.service.common.kafka.producer.implementation;

import com.example.transaction_service.model.log.entity.DatasourceErrorLog;
import com.example.transaction_service.model.transaction.entity.Transaction;
import com.example.transaction_service.service.common.kafka.producer.KafkaProducerService;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Реализация сервиса {@link KafkaProducerService} для K = <b>{@link String}</b> V = <b>{@link Transaction}</b>
 */
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
