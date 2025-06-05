package com.example.transaction_manager_service.service.kafka.producer.router.implementation;

import com.example.transaction_manager_service.exception.NotFoundException;
import com.example.transaction_manager_service.model.transaction.entity.Transaction;
import com.example.transaction_manager_service.service.kafka.producer.KafkaProducerService;
import com.example.transaction_manager_service.service.kafka.producer.router.KafkaProducerServiceRouter;
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

    public KafkaProducerServiceRouterManager(KafkaProducerService<String, Transaction> transactionKafkaProducerService) {
        this.kafkaProducerServiceMap = Map.of(Transaction.class, transactionKafkaProducerService);
    }


    @Override
    public <K extends Serializable, V extends Serializable> KafkaProducerService<K, V> getKafkaProducerService(Class<V> targetClass) {
        return Optional.ofNullable((KafkaProducerService<K, V>) kafkaProducerServiceMap.get(targetClass)).orElseThrow(() -> new NotFoundException(String.format("unknown kafka producer by target class\ntarget class : %s", targetClass)));
    }
}
