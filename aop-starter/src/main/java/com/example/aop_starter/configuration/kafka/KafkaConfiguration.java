package com.example.aop_starter.configuration.kafka;

import com.example.aop_starter.configuration.kafka.properties.KafkaProperties;
import com.example.aop_starter.configuration.kafka.properties.SpringKafkaProperties;
import com.example.aop_starter.model.log.entity.DatasourceErrorLog;
import com.example.aop_starter.model.log.entity.TimeLimitExceedLog;
import com.example.aop_starter.service.common.kafka.producer.KafkaProducerService;
import com.example.aop_starter.service.common.kafka.producer.implementation.DatasourceErrorLogKafkaProducerService;
import com.example.aop_starter.service.common.kafka.producer.implementation.TimeLimitExceedLogKafkaProducerService;
import com.example.aop_starter.service.common.kafka.producer.router.KafkaProducerServiceRouter;
import com.example.aop_starter.service.common.kafka.producer.router.implementation.KafkaProducerServiceRouterManager;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableConfigurationProperties({KafkaProperties.class, SpringKafkaProperties.class})
public class KafkaConfiguration {
    private SpringKafkaProperties springKafkaProperties;

    public KafkaConfiguration(SpringKafkaProperties springKafkaProperties) {
        this.springKafkaProperties = springKafkaProperties;
    }

    @Bean
    @ConditionalOnMissingBean
    public NewTopic t1_demo_metricsTopic(KafkaProperties kafkaProperties) {
        return TopicBuilder
                .name(kafkaProperties.getMetric().getName())
                .replicas(kafkaProperties.getReplicationFactor())
                .partitions(kafkaProperties.getReplicationFactor())
                .build();
    }

    @Bean
    @ConditionalOnMissingBean
    public ProducerFactory<String, DatasourceErrorLog> datasourceErrorLogKafkaProducer(ObjectMapper objectMapper) {
        DefaultKafkaProducerFactory<String, DatasourceErrorLog> producerFactory = new DefaultKafkaProducerFactory<>(buildAtMostOnceProducerProperties());
        producerFactory.setValueSerializer(new JsonSerializer<>(objectMapper));

        return producerFactory;
    }

    @Bean
    @ConditionalOnMissingBean
    public ProducerFactory<String, TimeLimitExceedLog> timeLimitExceedLogKafkaProducer(ObjectMapper objectMapper) {
        DefaultKafkaProducerFactory<String, TimeLimitExceedLog> producerFactory = new DefaultKafkaProducerFactory<>(buildAtMostOnceProducerProperties());
        producerFactory.setValueSerializer(new JsonSerializer<>(objectMapper));

        return producerFactory;
    }

    @Bean
    @ConditionalOnMissingBean
    public KafkaTemplate<String, DatasourceErrorLog> datasourceErrorLogKafkaTemplate(ProducerFactory<String, DatasourceErrorLog> producerFactory) {
        return new KafkaTemplate<>(producerFactory);
    }

    @Bean
    @ConditionalOnMissingBean
    public KafkaTemplate<String, TimeLimitExceedLog> timeLimitExceedLogKafkaTemplate(ProducerFactory<String, TimeLimitExceedLog> producerFactory) {
        return new KafkaTemplate<>(producerFactory);
    }

    @Bean
    @ConditionalOnMissingBean
    public KafkaProducerService<String, DatasourceErrorLog> datasourceErrorLogKafkaProducerService(KafkaTemplate<String, DatasourceErrorLog> datasourceErrorLogKafkaTemplate){
        return new DatasourceErrorLogKafkaProducerService(datasourceErrorLogKafkaTemplate);
    }

    @Bean
    @ConditionalOnMissingBean
    public KafkaProducerService<String, TimeLimitExceedLog> timeLimitExceedLogKafkaProducerService(KafkaTemplate<String, TimeLimitExceedLog> timeLimitExceedLogKafkaTemplate){
        return new TimeLimitExceedLogKafkaProducerService(timeLimitExceedLogKafkaTemplate);
    }

    @Bean
    @ConditionalOnMissingBean
    public KafkaProducerServiceRouter kafkaProducerServiceRouter(KafkaProducerService<String, DatasourceErrorLog> datasourceErrorLogKafkaProducerService, KafkaProducerService<String, TimeLimitExceedLog> timeLimitExceedLogKafkaProducerService){
        return new KafkaProducerServiceRouterManager(datasourceErrorLogKafkaProducerService, timeLimitExceedLogKafkaProducerService);
    }

    @Bean
    @ConditionalOnMissingBean
    public ObjectMapper jsonMapper() {
        return new JsonMapper();
    }

    private Map<String, Object> buildAtMostOnceProducerProperties() {
        Map<String, Object> producerProperties = new HashMap<>();

        producerProperties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        producerProperties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        producerProperties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, springKafkaProperties.getBootstrapServers());
        producerProperties.put(ProducerConfig.ACKS_CONFIG, "0");
        producerProperties.put(ProducerConfig.RETRIES_CONFIG, 0);

        return producerProperties;
    }
}
