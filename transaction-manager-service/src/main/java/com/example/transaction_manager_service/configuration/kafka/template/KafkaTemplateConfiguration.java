package com.example.transaction_manager_service.configuration.kafka.template;

import com.example.transaction_manager_service.environment.kafka.KafkaEnvironment;
import com.example.transaction_manager_service.model.transaction.entity.Transaction;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

/**
 * Конфигурация для KafkaTemplate
 */
@Configuration
public class KafkaTemplateConfiguration {
    private final KafkaEnvironment kafkaEnvironment;

    public KafkaTemplateConfiguration(KafkaEnvironment kafkaEnvironment) {
        this.kafkaEnvironment = kafkaEnvironment;
    }

    @Bean
    public KafkaTemplate<String, Transaction> transactionKafkaTemplate(ProducerFactory<String, Transaction> producerFactory) {
        KafkaTemplate<String, Transaction> kafkaTemplate = new KafkaTemplate<>(producerFactory);
//        kafkaTemplate.setTransactionIdPrefix(kafkaEnvironment.getKAFKA_TOPIC_TRANSACTION_TRANSACTION_ID());
        return kafkaTemplate;
    }
}
