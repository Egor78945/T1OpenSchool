package com.example.transaction_manager_service.service.transaction.implementation;

import com.example.transaction_manager_service.environment.kafka.KafkaEnvironment;
import com.example.transaction_manager_service.environment.transaction.TransactionEnvironment;
import com.example.transaction_manager_service.model.transaction.entity.Transaction;
import com.example.transaction_manager_service.model.transaction.status.entity.TransactionStatus;
import com.example.transaction_manager_service.model.transaction.status.enumeration.TransactionStatusEnumeration;
import com.example.transaction_manager_service.service.kafka.producer.KafkaProducerService;
import com.example.transaction_manager_service.service.transaction.TransactionProcessorService;
import com.example.transaction_manager_service.service.transaction.TransactionService;
import com.example.transaction_manager_service.service.transaction.status.TransactionStatusService;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class TransactionProcessorServiceManager implements TransactionProcessorService<Transaction> {
    private final KafkaEnvironment kafkaEnvironment;
    private final TransactionEnvironment transactionEnvironment;
    private final TransactionService<Transaction> transactionService;
    private final TransactionStatusService<TransactionStatus> transactionStatusService;
    private final KafkaProducerService<String, Transaction> transactionKafkaProducerService;

    public TransactionProcessorServiceManager(KafkaEnvironment kafkaEnvironment, TransactionEnvironment transactionEnvironment, @Qualifier("transactionServiceManager") TransactionService<Transaction> transactionService, @Qualifier("transactionStatusServiceManager") TransactionStatusService<TransactionStatus> transactionStatusService, @Qualifier("transactionKafkaProducerService") KafkaProducerService<String, Transaction> transactionKafkaProducerService) {
        this.kafkaEnvironment = kafkaEnvironment;
        this.transactionEnvironment = transactionEnvironment;
        this.transactionService = transactionService;
        this.transactionStatusService = transactionStatusService;
        this.transactionKafkaProducerService = transactionKafkaProducerService;
    }

    @Override
    public void accept(Transaction transaction) {
        List<Transaction> transactions = transactionService.getBySenderIdAfter(transaction.getSender().getId(), Timestamp.valueOf(Timestamp.valueOf(LocalDateTime.now()).toInstant().minus(transactionEnvironment.getTRANSACTION_REQUEST_PER_MINUTE(), ChronoUnit.MINUTES).toString()));
        if (transactions.size() + 1 > transactionEnvironment.getTRANSACTION_REQUEST_MAX_COUNT()) {
            transactions.add(transaction);
            for(Transaction t: transactions){
                reject(t);
            }
        } else {
            transaction.setTransactionStatus(transactionStatusService.getById(TransactionStatusEnumeration.ACCEPTED.getId()));
            transactionKafkaProducerService.send(new ProducerRecord<>(kafkaEnvironment.getKAFKA_TOPIC_TRANSACTION_RESULT(), transaction.getTransactionType().getName(), transaction));
        }
    }

    @Override
    public void reject(Transaction transaction) {
        transaction.setTransactionStatus(transactionStatusService.getById(TransactionStatusEnumeration.BLOCKED.getId()));
        transactionKafkaProducerService.send(new ProducerRecord<>(kafkaEnvironment.getKAFKA_TOPIC_TRANSACTION_RESULT(), transaction.getTransactionType().getName(), transaction));
    }
}
