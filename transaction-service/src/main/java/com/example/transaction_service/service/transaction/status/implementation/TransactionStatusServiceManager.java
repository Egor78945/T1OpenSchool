package com.example.transaction_service.service.transaction.status.implementation;

import com.example.transaction_service.exception.NotFoundException;
import com.example.transaction_service.model.transaction.status.entity.TransactionStatus;
import com.example.transaction_service.repository.TransactionStatusRepository;
import com.example.transaction_service.service.common.aop.annotation.Cached;
import com.example.transaction_service.service.transaction.status.TransactionStatusService;
import org.springframework.stereotype.Service;

/**
 * Реализация {@link TransactionStatusService}
 */
@Service
public class TransactionStatusServiceManager implements TransactionStatusService<TransactionStatus> {
    private final TransactionStatusRepository transactionStatusRepository;

    public TransactionStatusServiceManager(TransactionStatusRepository transactionStatusRepository) {
        this.transactionStatusRepository = transactionStatusRepository;
    }

    @Override
    @Cached
    public TransactionStatus getById(long id) {
        return transactionStatusRepository.findById(id).orElseThrow(() -> new NotFoundException(String.format("unknown transaction status by id\ntransaction status id : %s", id)));
    }
}
