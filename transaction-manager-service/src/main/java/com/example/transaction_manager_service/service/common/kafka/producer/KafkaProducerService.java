package com.example.transaction_manager_service.service.common.kafka.producer;

import org.apache.kafka.clients.producer.ProducerRecord;

import java.io.Serializable;

public interface KafkaProducerService<K extends Serializable, V extends Serializable> {
    void send(ProducerRecord<K, V> producerRecord);
}
