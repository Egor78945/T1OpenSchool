package com.example.transaction_service.configuration.kafka.transaction;

import com.example.transaction_service.model.transaction.entity.Transaction;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.transaction.KafkaTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

/**
 * Конфигурация менеджеров транзакций сообщений в Kafka
 */
@Configuration
public class KafkaTransactionManagementConfiguration {
    @Bean
    public PlatformTransactionManager kafkaTransactionTransactionManager(ProducerFactory<String, Transaction> producerFactory){
        return new KafkaTransactionManager<>(producerFactory);
    }
}
