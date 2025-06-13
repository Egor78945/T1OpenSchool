package com.example.aop_starter.service.common.kafka.producer.router;


import com.example.aop_starter.service.common.kafka.producer.StarterKafkaProducerService;

import java.io.Serializable;


public interface StarterKafkaProducerServiceRouter {
    <K extends Serializable, V extends Serializable> StarterKafkaProducerService<K,V> getKafkaProducerService(Class<V> targetClass);
}
