package com.example.transaction_service.service.common.kafka.producer.router.implementation;

import com.example.transaction_service.exception.NotFoundException;
import com.example.transaction_service.model.log.entity.DatasourceErrorLog;
import com.example.transaction_service.model.log.entity.TimeLimitExceedLog;
import com.example.transaction_service.model.transaction.entity.Transaction;
import com.example.transaction_service.service.common.kafka.producer.KafkaProducerService;
import com.example.transaction_service.service.common.kafka.producer.router.KafkaProducerServiceRouter;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Map;
import java.util.Optional;

/**
 * Класс, реализующий интерфейс {@link KafkaProducerServiceRouter}
 */
@Component
public class KafkaProducerServiceRouterManager implements KafkaProducerServiceRouter {
    private final Map<Class<? extends Serializable>, KafkaProducerService<? extends Serializable, ? extends Serializable>> kafkaProducerServiceMap;

    public KafkaProducerServiceRouterManager(KafkaProducerService<String, DatasourceErrorLog> datasourceErrorLogKafkaProducerService, KafkaProducerService<String, TimeLimitExceedLog> timeLimitExceedLogKafkaProducerService, KafkaProducerService<String, Transaction> transactionKafkaProducerService) {
        this.kafkaProducerServiceMap = Map.of(DatasourceErrorLog.class, datasourceErrorLogKafkaProducerService, TimeLimitExceedLog.class, timeLimitExceedLogKafkaProducerService, Transaction.class, transactionKafkaProducerService);
    }


    @Override
    public <K extends Serializable, V extends Serializable> KafkaProducerService<K, V> getKafkaProducerService(Class<V> targetClass) {
        return Optional.ofNullable((KafkaProducerService<K, V>) kafkaProducerServiceMap.get(targetClass)).orElseThrow(() -> new NotFoundException(String.format("unknown kafka producer by target class\ntarget class : %s", targetClass)));
    }
}
