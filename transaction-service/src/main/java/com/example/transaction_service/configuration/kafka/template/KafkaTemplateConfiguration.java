package com.example.transaction_service.configuration.kafka.template;

import com.example.transaction_service.environment.kafka.KafkaEnvironment;
import com.example.transaction_service.model.log.entity.DatasourceErrorLog;
import com.example.transaction_service.model.log.entity.TimeLimitExceedLog;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.messaging.Message;

@Configuration
public class KafkaTemplateConfiguration {
    private final KafkaEnvironment kafkaEnvironment;

    public KafkaTemplateConfiguration(KafkaEnvironment kafkaEnvironment) {
        this.kafkaEnvironment = kafkaEnvironment;
    }

    @Bean
    public KafkaTemplate<String, DatasourceErrorLog> datasourceErrorLogKafkaTemplate(ProducerFactory<String, DatasourceErrorLog> producerFactory) {
        return new KafkaTemplate<>(producerFactory);
    }

    @Bean
    public KafkaTemplate<String, TimeLimitExceedLog> timeLimitExceedLogKafkaTemplate(ProducerFactory<String, TimeLimitExceedLog> producerFactory) {
        return new KafkaTemplate<>(producerFactory);
    }
}
