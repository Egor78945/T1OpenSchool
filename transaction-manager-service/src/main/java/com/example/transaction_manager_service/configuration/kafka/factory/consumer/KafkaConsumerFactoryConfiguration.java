package com.example.transaction_manager_service.configuration.kafka.factory.consumer;

import com.example.transaction_manager_service.environment.kafka.KafkaEnvironment;
import com.example.transaction_manager_service.model.transaction.entity.Transaction;
import com.example.transaction_service.environment.kafka.KafkaEnvironment;
import com.example.transaction_service.model.transaction.entity.Transaction;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

/**
 * Класс, ответственный за конфигурацию {@link ConsumerFactory}
 */
@Configuration
public class KafkaConsumerFactoryConfiguration {
    private final KafkaEnvironment kafkaEnvironment;

    public KafkaConsumerFactoryConfiguration(KafkaEnvironment kafkaEnvironment) {
        this.kafkaEnvironment = kafkaEnvironment;
    }

    @Bean
    public ConsumerFactory<String, Transaction> transactionConsumerFactory(ObjectMapper objectMapper) {
        var kafkaConsumerFactory = new DefaultKafkaConsumerFactory<String, Transaction>(buildKafkaConsumerProperties("com.example.transaction_service.model.transaction.entity.Transaction", "com.example.transaction_service.model.transaction.entity.Transaction", kafkaEnvironment.getKAFKA_TOPIC_TRANSACTION_TRANSACTION_ID()));
        kafkaConsumerFactory.setValueDeserializer(new JsonDeserializer<>(objectMapper));

        return kafkaConsumerFactory;
    }

    /**
     * Моделирует базовые конфигурации для Consumer Factory
     * @param classPathFrom Объект, который должен быть десериализован
     * @param classPathTo Объект, в который должен быть десериализован полученый объект
     * @param groupId Группа Consumer'ов
     * @return {@link Map}, хранящий нужные настройки
     */
    private Map<String, Object> buildKafkaConsumerProperties(String classPathFrom, String classPathTo, String groupId){
        Map<String, Object> properties = new HashMap<>();

        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        properties.put(JsonDeserializer.TYPE_MAPPINGS, String.format("%s:%s", classPathFrom, classPathTo));
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaEnvironment.getKAFKA_BOOTSTRAP_SERVER());
        properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, kafkaEnvironment.getKAFKA_AUTO_OFFSET_RESET());

        return properties;
    }
}
