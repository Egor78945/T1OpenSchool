package com.example.transaction_manager_service.configuration.kafka.factory.producer;

import com.example.transaction_manager_service.environment.kafka.KafkaEnvironment;
import com.example.transaction_manager_service.model.transaction.entity.Transaction;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

/**
 * Класс, отвечающий за конфигурацию {@link ProducerFactory}
 */
@Configuration
public class KafkaProducerFactoryConfiguration {
    private final KafkaEnvironment kafkaEnvironment;

    public KafkaProducerFactoryConfiguration(KafkaEnvironment kafkaEnvironment) {
        this.kafkaEnvironment = kafkaEnvironment;
    }

    @Bean
    public ProducerFactory<String, Transaction> transactionKafkaProducer(ObjectMapper objectMapper) {
        DefaultKafkaProducerFactory<String, Transaction> producerFactory = new DefaultKafkaProducerFactory<>(buildAtMostOnceProducerProperties());
        producerFactory.setValueSerializer(new JsonSerializer<>(objectMapper));

        return producerFactory;
    }

    /**
     * Моделирует базовые конфигурации для {@link ProducerFactory}, соответствующие принципу '<b>EXACTLY ONCE</b>'
     * @param transactionId Идентификатор для транзакции
     * @return {@link Map}, хранящий нужные настройки
     */
    private Map<String, Object> buildExactlyOnceProducerProperties(String transactionId) {
        Map<String, Object> producerProperties = new HashMap<>();

        producerProperties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        producerProperties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        producerProperties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaEnvironment.getKAFKA_BOOTSTRAP_SERVER());
        producerProperties.put(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, true);
        producerProperties.put(ProducerConfig.ACKS_CONFIG, "all");
        producerProperties.put(ProducerConfig.TRANSACTIONAL_ID_CONFIG, transactionId);
        producerProperties.put(ProducerConfig.RETRIES_CONFIG, kafkaEnvironment.getKAFKA_PRODUCER_RETRY_COUNT());

        return producerProperties;
    }

    /**
     * Моделирует базовые конфигурации для {@link ProducerFactory}, соответствующие принципу '<b>AT MOST ONCE</b>'
     * @return {@link Map}, хранящий нужные настройки
     */
    private Map<String, Object> buildAtMostOnceProducerProperties() {
        Map<String, Object> producerProperties = new HashMap<>();

        producerProperties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        producerProperties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        producerProperties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaEnvironment.getKAFKA_BOOTSTRAP_SERVER());
        producerProperties.put(ProducerConfig.ACKS_CONFIG, "0");
        producerProperties.put(ProducerConfig.RETRIES_CONFIG, 0);

        return producerProperties;
    }
}
