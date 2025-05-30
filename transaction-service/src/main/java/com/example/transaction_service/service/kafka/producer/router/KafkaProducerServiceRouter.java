package com.example.transaction_service.service.kafka.producer.router;

import com.example.transaction_service.service.kafka.producer.KafkaProducerService;

import java.io.Serializable;
import java.util.Optional;

public interface KafkaProducerServiceRouter {
    <K extends Serializable, V extends Serializable> Optional<KafkaProducerService<K,V>> getKafkaProducerService(Class<V> targetClass);
}
