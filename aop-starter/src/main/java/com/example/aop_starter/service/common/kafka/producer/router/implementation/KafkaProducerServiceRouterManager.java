package com.example.aop_starter.service.common.kafka.producer.router.implementation;

import com.example.aop_starter.exception.NotFoundException;
import com.example.aop_starter.model.log.entity.DatasourceErrorLog;
import com.example.aop_starter.model.log.entity.TimeLimitExceedLog;
import com.example.aop_starter.service.common.kafka.producer.KafkaProducerService;
import com.example.aop_starter.service.common.kafka.producer.router.KafkaProducerServiceRouter;
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
    public <K extends Serializable, V extends Serializable> KafkaProducerService<K, V> getKafkaProducerService(Class<V> targetClass) {
        return Optional.ofNullable((KafkaProducerService<K, V>) kafkaProducerServiceMap.get(targetClass)).orElseThrow(() -> new NotFoundException(String.format("unknown kafka producer by target class\ntarget class : %s", targetClass)));
    }
}
