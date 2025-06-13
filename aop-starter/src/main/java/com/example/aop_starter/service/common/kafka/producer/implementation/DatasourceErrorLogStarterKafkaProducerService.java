package com.example.aop_starter.service.common.kafka.producer.implementation;

import com.example.aop_starter.model.log.entity.DatasourceErrorLog;
import com.example.aop_starter.service.common.kafka.producer.StarterKafkaProducerService;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.kafka.core.KafkaTemplate;

public class DatasourceErrorLogStarterKafkaProducerService implements StarterKafkaProducerService<String, DatasourceErrorLog> {
    private final KafkaTemplate<String, DatasourceErrorLog> kafkaTemplate;

    public DatasourceErrorLogStarterKafkaProducerService(KafkaTemplate<String, DatasourceErrorLog> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public void send(ProducerRecord<String, DatasourceErrorLog> producerRecord) {
        kafkaTemplate.send(producerRecord);
    }
}
