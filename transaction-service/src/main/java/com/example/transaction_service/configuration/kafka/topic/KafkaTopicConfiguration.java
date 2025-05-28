package com.example.transaction_service.configuration.kafka.topic;

import com.example.transaction_service.environment.kafka.KafkaEnvironment;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfiguration {
    private final KafkaEnvironment kafkaEnvironment;

    public KafkaTopicConfiguration(KafkaEnvironment kafkaEnvironment) {
        this.kafkaEnvironment = kafkaEnvironment;
    }

    @Bean
    public NewTopic t1_demo_metricsTopic(){
        return TopicBuilder
                .name(kafkaEnvironment.getKAFKA_TOPIC_METRIC_NAME())
                .replicas(kafkaEnvironment.getKAFKA_TOPIC_REPLICATION_FACTOR())
                .partitions(kafkaEnvironment.getKAFKA_TOPIC_REPLICATION_FACTOR())
                .build();
    }
}
