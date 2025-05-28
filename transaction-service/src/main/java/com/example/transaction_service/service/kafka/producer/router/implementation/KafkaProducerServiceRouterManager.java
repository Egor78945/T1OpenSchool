package com.example.transaction_service.service.kafka.producer.router.implementation;

import com.example.transaction_service.model.log.Log;
import com.example.transaction_service.model.log.entity.DatasourceErrorLog;
import com.example.transaction_service.model.log.entity.TimeLimitExceedLog;
import com.example.transaction_service.service.kafka.producer.KafkaProducerService;
import com.example.transaction_service.service.kafka.producer.implementation.DatasourceErrorLogKafkaProducerService;
import com.example.transaction_service.service.kafka.producer.implementation.TimeLimitExceedLogKafkaProducerService;
import com.example.transaction_service.service.kafka.producer.router.KafkaProducerServiceRouter;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Map;
import java.util.Optional;

@Component
public class KafkaProducerServiceRouterManager implements KafkaProducerServiceRouter<String, Log> {
    private final Map<Class<Log>, KafkaProducerService<String, Serializable>> kafkaProducerServiceByClass;

    public KafkaProducerServiceRouterManager(DatasourceErrorLogKafkaProducerService datasourceErrorLogKafkaProducerService, TimeLimitExceedLogKafkaProducerService timeLimitExceedLogKafkaProducerService) {
        this.kafkaProducerServiceByClass = Map.of(DatasourceErrorLog.class, datasourceErrorLogKafkaProducerService, TimeLimitExceedLog.class, timeLimitExceedLogKafkaProducerService);
    }

    @Override
    public Optional<KafkaProducerService<String, Log>> getKafkaProducerService(Class<Log> targetClass) {
        return Optional.ofNullable(kafkaProducerServiceByClass.get(targetClass));
    }
}
