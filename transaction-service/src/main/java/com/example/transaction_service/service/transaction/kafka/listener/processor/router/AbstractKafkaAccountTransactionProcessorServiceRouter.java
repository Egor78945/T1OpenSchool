package com.example.transaction_service.service.transaction.kafka.listener.processor.router;

import com.example.transaction_service.model.transaction.entity.Transaction;
import com.example.transaction_service.model.transaction.type.enumeration.TransactionTypeEnumeration;
import com.example.transaction_service.service.transaction.kafka.listener.processor.AbstractKafkaAccountTransactionProcessorService;

import java.io.Serializable;

public interface AbstractKafkaAccountTransactionProcessorServiceRouter <K extends Serializable, V extends Transaction> {
    AbstractKafkaAccountTransactionProcessorService<K, V> getBy(TransactionTypeEnumeration transactionTypeEnumeration);
}
