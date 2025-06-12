package com.example.aop_starter.service.common.kafka.producer.router;


import com.example.aop_starter.service.common.kafka.producer.KafkaProducerService;

import java.io.Serializable;


public interface KafkaProducerServiceRouter {
    <K extends Serializable, V extends Serializable> KafkaProducerService<K,V> getKafkaProducerService(Class<V> targetClass);
}
