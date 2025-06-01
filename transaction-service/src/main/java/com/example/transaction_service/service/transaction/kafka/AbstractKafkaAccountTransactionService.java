package com.example.transaction_service.service.transaction.kafka;

import com.example.transaction_service.environment.kafka.KafkaEnvironment;
import com.example.transaction_service.model.transaction.entity.Transaction;
import com.example.transaction_service.service.kafka.producer.KafkaProducerService;

import java.io.Serializable;

/**
 * Адстрактный класс, предоставляющий базовый функционал для взаимодействия с {@link Transaction} с помощью <b>Kafka</b>
 * @param <K> Тип, представляющий Key
 * @param <V> Тип, представляющий Value
 */
public abstract class AbstractKafkaAccountTransactionService<K extends Serializable, V extends Transaction> {
    protected final KafkaEnvironment kafkaEnvironment;
    protected final KafkaProducerService<K, V> kafkaProducerService;

    public AbstractKafkaAccountTransactionService(KafkaEnvironment kafkaEnvironment, KafkaProducerService<K, V> kafkaProducerService) {
        this.kafkaEnvironment = kafkaEnvironment;
        this.kafkaProducerService = kafkaProducerService;
    }

    /**
     * Сохранить {@link Transaction}, в качестве значения
     * @param key Ключ
     * @param transaction Значение, являющееся {@link Transaction} или его наследником
     */
    public abstract void save(K key, V transaction);
}
