package com.example.transaction_service.service.transaction.processor.router.implementation;

import com.example.transaction_service.exception.NotFoundException;
import com.example.transaction_service.model.transaction.entity.Transaction;
import com.example.transaction_service.model.transaction.type.enumeration.TransactionTypeEnumeration;
import com.example.transaction_service.service.transaction.processor.AbstractTransactionProcessorService;
import com.example.transaction_service.service.transaction.processor.implementation.InsertAbstractTransactionProcessorServiceManager;
import com.example.transaction_service.service.transaction.processor.implementation.TransferAbstractTransactionProcessorServiceManager;
import com.example.transaction_service.service.transaction.processor.router.TransactionProcessorServiceRouter;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
public class TransactionProcessorServiceRouterManagement implements TransactionProcessorServiceRouter<Transaction, AbstractTransactionProcessorService<Transaction>> {
    private final Map<TransactionTypeEnumeration, AbstractTransactionProcessorService<Transaction>> transactionProcessorServiceMap;

    public TransactionProcessorServiceRouterManagement(InsertAbstractTransactionProcessorServiceManager insertTransactionProcessorServiceManager, TransferAbstractTransactionProcessorServiceManager transferTransactionProcessorServiceManager) {
        this.transactionProcessorServiceMap = Map.of(TransactionTypeEnumeration.INSERT, insertTransactionProcessorServiceManager, TransactionTypeEnumeration.TRANSFER, transferTransactionProcessorServiceManager);
    }

    @Override
    public AbstractTransactionProcessorService<Transaction> getByTransactionType(TransactionTypeEnumeration transactionTypeEnumeration) {
        System.out.println("getByTransactionType");
        return Optional.ofNullable(transactionProcessorServiceMap.get(transactionTypeEnumeration)).orElseThrow(() -> new NotFoundException(String.format("transaction processor service is not found by transaction type\nTransactionType : %s", transactionTypeEnumeration)));
    }
}
