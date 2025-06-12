package com.example.aop_starter.configuration.kafka.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "kafka.topic")
public class KafkaProperties {

    private int replicationFactor;

    private Metric metric;

    public KafkaProperties(Metric metric, int replicationFactor) {
        this.metric = metric;
        this.replicationFactor = replicationFactor;
    }

    public KafkaProperties() {
        this.metric = new Metric();
    }

    public int getReplicationFactor() {
        return replicationFactor;
    }

    public void setReplicationFactor(int replicationFactor) {
        this.replicationFactor = replicationFactor;
    }

    public Metric getMetric() {
        return metric;
    }

    public void setMetric(Metric metric) {
        this.metric = metric;
    }

    public static class Metric {
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
