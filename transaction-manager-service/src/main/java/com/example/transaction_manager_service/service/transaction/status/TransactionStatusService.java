package com.example.transaction_manager_service.service.transaction.status;

import com.example.transaction_manager_service.model.transaction.status.entity.TransactionStatus;

public interface TransactionStatusService<T extends TransactionStatus> {
    T getById(long id);
}
