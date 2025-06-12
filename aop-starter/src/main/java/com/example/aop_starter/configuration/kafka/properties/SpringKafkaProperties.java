package com.example.aop_starter.configuration.kafka.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "spring.kafka")
public class SpringKafkaProperties {
    private String bootstrapServers;

    public SpringKafkaProperties(String bootstrapServers) {
        this.bootstrapServers = bootstrapServers;
    }

    public SpringKafkaProperties() {
    }

    public String getBootstrapServers() {
        return bootstrapServers;
    }

    public void setBootstrapServers(String bootstrapServers) {
        this.bootstrapServers = bootstrapServers;
    }
}
