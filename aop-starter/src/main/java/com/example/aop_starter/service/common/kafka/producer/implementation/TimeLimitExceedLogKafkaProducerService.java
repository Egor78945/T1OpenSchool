package com.example.aop_starter.service.common.kafka.producer.implementation;

import com.example.aop_starter.model.log.entity.TimeLimitExceedLog;
import com.example.aop_starter.service.common.kafka.producer.KafkaProducerService;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;


@Service
public class TimeLimitExceedLogKafkaProducerService implements KafkaProducerService<String, TimeLimitExceedLog> {
    private final KafkaTemplate<String, TimeLimitExceedLog> kafkaTemplate;

    public TimeLimitExceedLogKafkaProducerService(KafkaTemplate<String, TimeLimitExceedLog> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public void send(ProducerRecord<String, TimeLimitExceedLog> producerRecord) {
        kafkaTemplate.send(producerRecord);
    }
}
