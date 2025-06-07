package com.example.transaction_manager_service.service.common.kafka.producer;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.KafkaProducer;

import java.io.Serializable;

/**
 * Интерфейс, предоставляющий функционал для работы {@link KafkaProducer}
 * @param <K> Тип, представляющий ключ сообщения
 * @param <V> Тип, представляющий значение сообщения
 */
public interface KafkaProducerService<K extends Serializable, V extends Serializable> {
    /**
     * Отправить сообщение
     * @param producerRecord Сущность сообщения
     */
    void send(ProducerRecord<K, V> producerRecord);
}
