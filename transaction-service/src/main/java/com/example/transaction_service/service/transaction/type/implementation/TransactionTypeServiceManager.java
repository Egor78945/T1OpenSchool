package com.example.transaction_service.service.transaction.type.implementation;

import com.example.aop_starter.service.common.aop.annotation.Cached;
import com.example.transaction_service.exception.NotFoundException;
import com.example.transaction_service.model.transaction.type.entity.TransactionType;
import com.example.transaction_service.repository.TransactionTypeRepository;
import com.example.transaction_service.service.transaction.type.TransactionTypeService;
import org.springframework.stereotype.Service;

/**
 * Реализация {@link TransactionTypeService}
 */
@Service
public class TransactionTypeServiceManager implements TransactionTypeService<TransactionType> {
    private final TransactionTypeRepository transactionTypeRepository;

    public TransactionTypeServiceManager(TransactionTypeRepository transactionTypeRepository) {
        this.transactionTypeRepository = transactionTypeRepository;
    }

    @Override
    @Cached
    public TransactionType getById(long id) {
        return transactionTypeRepository.findById(id).orElseThrow(() -> new NotFoundException(String.format("unknown transaction type by id\ntransaction type id : %s", id)));
    }
}
