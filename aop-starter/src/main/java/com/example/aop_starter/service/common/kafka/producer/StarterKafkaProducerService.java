package com.example.aop_starter.service.common.kafka.producer;

import org.apache.kafka.clients.producer.ProducerRecord;

import java.io.Serializable;

public interface StarterKafkaProducerService<K extends Serializable, V extends Serializable> {
    void send(ProducerRecord<K, V> producerRecord);
}
