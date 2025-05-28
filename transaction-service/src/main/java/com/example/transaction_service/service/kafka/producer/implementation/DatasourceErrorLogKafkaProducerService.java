package com.example.transaction_service.service.kafka.producer.implementation;

import com.example.transaction_service.model.log.entity.DatasourceErrorLog;
import com.example.transaction_service.service.kafka.producer.KafkaProducerService;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class DatasourceErrorLogKafkaProducerService implements KafkaProducerService<String, DatasourceErrorLog> {
    private final KafkaTemplate<String, DatasourceErrorLog> kafkaTemplate;

    public DatasourceErrorLogKafkaProducerService(KafkaTemplate<String, DatasourceErrorLog> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public void send(ProducerRecord<String, DatasourceErrorLog> producerRecord) {
        kafkaTemplate.send(producerRecord);
    }
}
