package com.example.transaction_service.service.kafka.producer.router;

import com.example.transaction_service.service.kafka.producer.KafkaProducerService;

import java.io.Serializable;
import java.util.Optional;

/**
 * Интерфейс, предоставляющий функционал для маршрутизации {@link KafkaProducerService}
 */
public interface KafkaProducerServiceRouter {
    /**
     * Получить {@link KafkaProducerService} по типу класса значения
     * @param targetClass Тип класса значения
     * @return {@link KafkaProducerService}
     * @param <K> Тип объекта ключа
     * @param <V> Тип объекта значения
     */
    <K extends Serializable, V extends Serializable> Optional<KafkaProducerService<K,V>> getKafkaProducerService(Class<V> targetClass);
}
