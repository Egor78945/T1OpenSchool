package com.example.transaction_manager_service.environment.kafka;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class KafkaEnvironment {
    private final String KAFKA_BOOTSTRAP_SERVER;
    private final String KAFKA_AUTO_OFFSET_RESET;
    private final String KAFKA_TOPIC_TRANSACTION_ACCEPT;
    private final String KAFKA_TOPIC_TRANSACTION_ACCEPT_TRANSACTION_ID;
    private final String KAFKA_TOPIC_TRANSACTION_RESULT;
    private final String KAFKA_TOPIC_TRANSACTION_RESULT_TRANSACTION_ID;
    private final int KAFKA_TOPIC_REPLICATION_FACTOR;
    private final int KAFKA_PRODUCER_RETRY_COUNT;

    public KafkaEnvironment(@Value("${spring.kafka.bootstrap-servers}") String KAFKA_BOOTSTRAP_SERVER,
                            @Value("${spring.kafka.consumer.auto-offset-reset}")String KAFKA_AUTO_OFFSET_RESET,
                            @Value("${kafka.topic.transaction-accept.name}")String KAFKA_TOPIC_TRANSACTION_ACCEPT,
                            @Value("${kafka.topic.transaction-accept.transaction-id}")String KAFKA_TOPIC_TRANSACTION_ACCEPT_TRANSACTION_ID,
                            @Value("${kafka.topic.transaction-result.name}") String KAFKA_TOPIC_TRANSACTION_RESULT,
                            @Value("${kafka.topic.transaction-result.transaction-id}") String KAFKA_TOPIC_TRANSACTION_RESULT_TRANSACTION_ID,
                            @Value("${kafka.topic.replication-factor}") int KAFKA_TOPIC_REPLICATION_FACTOR,
                            @Value("${spring.kafka.producer.retries}") int KAFKA_PRODUCER_RETRY_COUNT) {
        this.KAFKA_BOOTSTRAP_SERVER = KAFKA_BOOTSTRAP_SERVER;
        this.KAFKA_AUTO_OFFSET_RESET = KAFKA_AUTO_OFFSET_RESET;
        this.KAFKA_TOPIC_TRANSACTION_ACCEPT = KAFKA_TOPIC_TRANSACTION_ACCEPT;
        this.KAFKA_TOPIC_TRANSACTION_ACCEPT_TRANSACTION_ID = KAFKA_TOPIC_TRANSACTION_ACCEPT_TRANSACTION_ID;
        this.KAFKA_TOPIC_TRANSACTION_RESULT = KAFKA_TOPIC_TRANSACTION_RESULT;
        this.KAFKA_TOPIC_TRANSACTION_RESULT_TRANSACTION_ID = KAFKA_TOPIC_TRANSACTION_RESULT_TRANSACTION_ID;
        this.KAFKA_TOPIC_REPLICATION_FACTOR = KAFKA_TOPIC_REPLICATION_FACTOR;
        this.KAFKA_PRODUCER_RETRY_COUNT = KAFKA_PRODUCER_RETRY_COUNT;
    }

    public String getKAFKA_BOOTSTRAP_SERVER() {
        return KAFKA_BOOTSTRAP_SERVER;
    }

    public String getKAFKA_AUTO_OFFSET_RESET() {
        return KAFKA_AUTO_OFFSET_RESET;
    }

    public String getKAFKA_TOPIC_TRANSACTION_ACCEPT() {
        return KAFKA_TOPIC_TRANSACTION_ACCEPT;
    }

    public String getKAFKA_TOPIC_TRANSACTION_ACCEPT_TRANSACTION_ID() {
        return KAFKA_TOPIC_TRANSACTION_ACCEPT_TRANSACTION_ID;
    }

    public String getKAFKA_TOPIC_TRANSACTION_RESULT() {
        return KAFKA_TOPIC_TRANSACTION_RESULT;
    }

    public String getKAFKA_TOPIC_TRANSACTION_RESULT_TRANSACTION_ID() {
        return KAFKA_TOPIC_TRANSACTION_RESULT_TRANSACTION_ID;
    }

    public int getKAFKA_TOPIC_REPLICATION_FACTOR() {
        return KAFKA_TOPIC_REPLICATION_FACTOR;
    }

    public int getKAFKA_PRODUCER_RETRY_COUNT() {
        return KAFKA_PRODUCER_RETRY_COUNT;
    }
}
