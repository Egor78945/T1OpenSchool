package com.example.aop_starter.service.common.kafka.producer.implementation;

import com.example.aop_starter.model.log.entity.TimeLimitExceedLog;
import com.example.aop_starter.service.common.kafka.producer.StarterKafkaProducerService;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;


@Service
public class TimeLimitExceedLogStarterKafkaProducerService implements StarterKafkaProducerService<String, TimeLimitExceedLog> {
    private final KafkaTemplate<String, TimeLimitExceedLog> kafkaTemplate;

    public TimeLimitExceedLogStarterKafkaProducerService(KafkaTemplate<String, TimeLimitExceedLog> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public void send(ProducerRecord<String, TimeLimitExceedLog> producerRecord) {
        kafkaTemplate.send(producerRecord);
    }
}
