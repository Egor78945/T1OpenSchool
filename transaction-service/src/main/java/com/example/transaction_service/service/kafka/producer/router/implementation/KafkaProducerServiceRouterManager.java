package com.example.transaction_service.service.kafka.producer.router.implementation;

import com.example.transaction_service.model.log.Log;
import com.example.transaction_service.model.log.entity.DatasourceErrorLog;
import com.example.transaction_service.model.log.entity.TimeLimitExceedLog;
import com.example.transaction_service.service.kafka.producer.KafkaProducerService;
import com.example.transaction_service.service.kafka.producer.router.KafkaProducerServiceRouter;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Map;
import java.util.Optional;

@Component
public class KafkaProducerServiceRouterManager implements KafkaProducerServiceRouter {
    private final Map<Class<? extends Serializable>, KafkaProducerService<? extends Serializable, ? extends Serializable>> kafkaProducerServiceMap;

    public KafkaProducerServiceRouterManager(KafkaProducerService<String, DatasourceErrorLog> datasourceErrorLogKafkaProducerService, KafkaProducerService<String, TimeLimitExceedLog> timeLimitExceedLogKafkaProducerService) {
        this.kafkaProducerServiceMap = Map.of(DatasourceErrorLog.class, datasourceErrorLogKafkaProducerService, TimeLimitExceedLog.class, timeLimitExceedLogKafkaProducerService);
    }


    @Override
    public <K extends Serializable, V extends Serializable> Optional<KafkaProducerService<K, V>> getKafkaProducerService(Class<V> targetClass) {
        return Optional.ofNullable((KafkaProducerService<K, V>) kafkaProducerServiceMap.get(targetClass));
    }
}
