package com.example.transaction_manager_service.configuration.kafka.factory.listener;

import com.example.transaction_manager_service.model.transaction.entity.Transaction;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;

/**
 * Класс, ответственный за конфигурацию {@link KafkaListenerContainerFactory}
 */
@Configuration
public class KafkaListenerContainerFactoryConfiguration {
    @Bean
    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, Transaction>> transactionListenerContainerFactory(ConsumerFactory<String, Transaction> consumerFactory) {
        var factory = new ConcurrentKafkaListenerContainerFactory<String, Transaction>();
        factory.setConsumerFactory(consumerFactory);

        return factory;
    }
}