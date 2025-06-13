package com.example.transaction_service.configuration.kafka.topic;

import com.example.transaction_service.environment.kafka.KafkaEnvironment;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

/**
 * Конфигурация топиков в Kafka
 */
@Configuration
public class KafkaTopicConfiguration {
    private final KafkaEnvironment kafkaEnvironment;

    public KafkaTopicConfiguration(KafkaEnvironment kafkaEnvironment) {
        this.kafkaEnvironment = kafkaEnvironment;
    }

    @Bean
    public NewTopic t1_demo_transaction() {
        return TopicBuilder
                .name(kafkaEnvironment.getKAFKA_TOPIC_TRANSACTION())
                .replicas(kafkaEnvironment.getKAFKA_TOPIC_REPLICATION_FACTOR())
                .partitions(kafkaEnvironment.getKAFKA_TOPIC_REPLICATION_FACTOR())
                .build();
    }

    @Bean
    public NewTopic t1_demo_transaction_accept() {
        return TopicBuilder
                .name(kafkaEnvironment.getKAFKA_TOPIC_TRANSACTION_ACCEPT())
                .replicas(kafkaEnvironment.getKAFKA_TOPIC_REPLICATION_FACTOR())
                .partitions(kafkaEnvironment.getKAFKA_TOPIC_REPLICATION_FACTOR())
                .build();
    }

    @Bean
    public NewTopic t1_demo_transaction_result() {
        return TopicBuilder
                .name(kafkaEnvironment.getKAFKA_TOPIC_TRANSACTION_RESULT())
                .replicas(kafkaEnvironment.getKAFKA_TOPIC_REPLICATION_FACTOR())
                .partitions(kafkaEnvironment.getKAFKA_TOPIC_REPLICATION_FACTOR())
                .build();
    }
}
