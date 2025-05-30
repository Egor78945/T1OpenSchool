package com.example.transaction_service.service.kafka.producer.implementation;

import com.example.transaction_service.model.log.entity.DatasourceErrorLog;
import com.example.transaction_service.model.log.entity.TimeLimitExceedLog;
import com.example.transaction_service.service.common.aop.annotation.LogDatasourceError;
import com.example.transaction_service.service.common.aop.annotation.Metric;
import com.example.transaction_service.service.kafka.producer.KafkaProducerService;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Реализация сервиса по маршрутизации Kafka producers {@link KafkaProducerService} для K = <b>{@link String}</b> V = <b>{@link TimeLimitExceedLog}</b>
 */
@Service
public class TimeLimitExceedLogKafkaProducerService implements KafkaProducerService<String, TimeLimitExceedLog> {
    private final KafkaTemplate<String, TimeLimitExceedLog> kafkaTemplate;

    public TimeLimitExceedLogKafkaProducerService(KafkaTemplate<String, TimeLimitExceedLog> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    //@Transactional(transactionManager = "kafkaTimeLimitExceedLogTransactionManager")
    public void send(ProducerRecord<String, TimeLimitExceedLog> producerRecord) {
        kafkaTemplate.send(producerRecord);
    }
}
