package com.example.transaction_service.service.transaction.kafka.listener.processor.router.implementation;

import com.example.transaction_service.exception.NotFoundException;
import com.example.transaction_service.model.transaction.entity.Transaction;
import com.example.transaction_service.model.transaction.type.enumeration.TransactionTypeEnumeration;
import com.example.transaction_service.service.transaction.kafka.listener.processor.AbstractKafkaAccountTransactionProcessorService;
import com.example.transaction_service.service.transaction.kafka.listener.processor.implementation.InsertKafkaAccountTransactionProcessorServiceManager;
import com.example.transaction_service.service.transaction.kafka.listener.processor.implementation.TransferKafkaAccountTransactionProcessorServiceManager;
import com.example.transaction_service.service.transaction.kafka.listener.processor.router.AbstractKafkaAccountTransactionProcessorServiceRouter;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
public class KafkaAccountTransactionProcessorServiceRouterManager implements AbstractKafkaAccountTransactionProcessorServiceRouter<String, Transaction> {
    private final Map<TransactionTypeEnumeration, AbstractKafkaAccountTransactionProcessorService<String, Transaction>> transactionProcessorServiceMap;

    public KafkaAccountTransactionProcessorServiceRouterManager(InsertKafkaAccountTransactionProcessorServiceManager insertProcessor, TransferKafkaAccountTransactionProcessorServiceManager transferProcessor) {
        this.transactionProcessorServiceMap = Map.of(TransactionTypeEnumeration.INSERT, insertProcessor, TransactionTypeEnumeration.TRANSFER, transferProcessor);
    }

    @Override
    public AbstractKafkaAccountTransactionProcessorService<String, Transaction> getBy(TransactionTypeEnumeration transactionTypeEnumeration) {
        return Optional.ofNullable(transactionProcessorServiceMap.get(transactionTypeEnumeration)).orElseThrow(() -> new NotFoundException(String.format("kafka account transaction processor service is not found by transaction type\nTransactionType : %s", transactionTypeEnumeration)));
    }
}
