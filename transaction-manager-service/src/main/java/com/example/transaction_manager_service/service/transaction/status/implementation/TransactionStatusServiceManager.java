package com.example.transaction_manager_service.service.transaction.status.implementation;

import com.example.transaction_manager_service.exception.NotFoundException;
import com.example.transaction_manager_service.model.transaction.status.entity.TransactionStatus;
import com.example.transaction_manager_service.repository.TransactionStatusRepository;
import com.example.transaction_manager_service.service.transaction.status.TransactionStatusService;
import org.springframework.stereotype.Service;

@Service
public class TransactionStatusServiceManager implements TransactionStatusService<TransactionStatus> {
    private final TransactionStatusRepository transactionStatusRepository;

    public TransactionStatusServiceManager(TransactionStatusRepository transactionStatusRepository) {
        this.transactionStatusRepository = transactionStatusRepository;
    }

    @Override
    public TransactionStatus getById(long id) {
        return transactionStatusRepository.findById(id).orElseThrow(() -> new NotFoundException(String.format("transaction status is not found by id\ntransaction status id : %s", id)));
    }
}
