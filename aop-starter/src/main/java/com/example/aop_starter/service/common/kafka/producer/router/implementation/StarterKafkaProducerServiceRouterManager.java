package com.example.aop_starter.service.common.kafka.producer.router.implementation;

import com.example.aop_starter.exception.ObjectNotFoundException;
import com.example.aop_starter.model.log.entity.DatasourceErrorLog;
import com.example.aop_starter.model.log.entity.TimeLimitExceedLog;
import com.example.aop_starter.service.common.kafka.producer.StarterKafkaProducerService;
import com.example.aop_starter.service.common.kafka.producer.router.StarterKafkaProducerServiceRouter;

import java.io.Serializable;
import java.util.Map;
import java.util.Optional;

public class StarterKafkaProducerServiceRouterManager implements StarterKafkaProducerServiceRouter {
    private final Map<Class<? extends Serializable>, StarterKafkaProducerService<? extends Serializable, ? extends Serializable>> kafkaProducerServiceMap;

    public StarterKafkaProducerServiceRouterManager(StarterKafkaProducerService<String, DatasourceErrorLog> datasourceErrorLogStarterKafkaProducerService, StarterKafkaProducerService<String, TimeLimitExceedLog> timeLimitExceedLogStarterKafkaProducerService) {
        this.kafkaProducerServiceMap = Map.of(DatasourceErrorLog.class, datasourceErrorLogStarterKafkaProducerService, TimeLimitExceedLog.class, timeLimitExceedLogStarterKafkaProducerService);
    }


    @Override
    public <K extends Serializable, V extends Serializable> StarterKafkaProducerService<K, V> getKafkaProducerService(Class<V> targetClass) {
        return Optional.ofNullable((StarterKafkaProducerService<K, V>) kafkaProducerServiceMap.get(targetClass)).orElseThrow(() -> new ObjectNotFoundException(String.format("unknown kafka producer by target class\ntarget class : %s", targetClass)));
    }
}
