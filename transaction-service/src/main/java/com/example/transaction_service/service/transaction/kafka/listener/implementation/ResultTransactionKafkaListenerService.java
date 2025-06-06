package com.example.transaction_service.service.transaction.kafka.listener.implementation;

import com.example.transaction_service.model.transaction.entity.Transaction;
import com.example.transaction_service.model.transaction.type.enumeration.TransactionTypeEnumeration;
import com.example.transaction_service.service.common.aop.annotation.LogDatasourceError;
import com.example.transaction_service.service.transaction.processor.AbstractTransactionProcessorService;
import com.example.transaction_service.service.transaction.kafka.listener.KafkaListenerService;
import com.example.transaction_service.service.transaction.processor.router.TransactionProcessorServiceRouter;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class ResultTransactionKafkaListenerService implements KafkaListenerService<String, Transaction> {
    private final TransactionProcessorServiceRouter<Transaction, AbstractTransactionProcessorService<Transaction>> transactionProcessorServiceRouter;

    public ResultTransactionKafkaListenerService(TransactionProcessorServiceRouter<Transaction, AbstractTransactionProcessorService<Transaction>> transactionProcessorServiceRouter) {
        this.transactionProcessorServiceRouter = transactionProcessorServiceRouter;
    }

    @Override
    @KafkaListener(topics = "${kafka.topic.transaction-result.name}", groupId = "${kafka.topic.transaction-result.transaction-id}", containerFactory = "transactionListenerContainerFactory")
    @LogDatasourceError
    public void listen(ConsumerRecord<String, Transaction> listenableObject) {
        Transaction transaction = listenableObject.value();
        transactionProcessorServiceRouter.getByTransactionType(TransactionTypeEnumeration.getById(transaction.getTransactionType().getId())).process(transaction);
    }
}
