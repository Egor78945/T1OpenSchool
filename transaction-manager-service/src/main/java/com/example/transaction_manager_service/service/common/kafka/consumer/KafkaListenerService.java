package com.example.transaction_manager_service.service.common.kafka.consumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;

import java.io.Serializable;

/**
 * Интерфейс, предоставляющий функционал для чтения сообщений из Kafka topic
 * @param <K> Тип, являющийся key
 * @param <V> Тип, являющийся alue
 */
public interface KafkaListenerService<K extends Serializable, V extends Serializable> {
    /**
     * Получить сообщение из Kafka topic
     * @param listenableObject Сообщение
     */
    void listen(ConsumerRecord<K, V> listenableObject);
}
