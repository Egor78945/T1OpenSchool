package com.example.transaction_service.service.transaction.processor.router;

import com.example.transaction_service.model.transaction.entity.Transaction;
import com.example.transaction_service.model.transaction.type.enumeration.TransactionTypeEnumeration;
import com.example.transaction_service.service.transaction.processor.AbstractTransactionProcessorService;

public interface TransactionProcessorServiceRouter<T extends Transaction, S extends AbstractTransactionProcessorService<T>> {
    S getByTransactionType(TransactionTypeEnumeration transactionTypeEnumeration);
}
