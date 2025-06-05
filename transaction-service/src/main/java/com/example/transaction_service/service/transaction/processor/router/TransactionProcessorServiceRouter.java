package com.example.transaction_service.service.transaction.processor.router;

import com.example.transaction_service.model.transaction.entity.Transaction;
import com.example.transaction_service.model.transaction.status.enumeration.TransactionStatusEnumeration;
import com.example.transaction_service.model.transaction.type.enumeration.TransactionTypeEnumeration;
import com.example.transaction_service.service.transaction.TransactionProcessorService;

public interface TransactionProcessorServiceRouter<T extends Transaction, S extends TransactionProcessorService<T>> {
    S getByTransactionType(TransactionTypeEnumeration transactionTypeEnumeration);
}
