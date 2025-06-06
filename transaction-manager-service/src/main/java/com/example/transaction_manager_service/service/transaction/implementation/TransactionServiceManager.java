package com.example.transaction_manager_service.service.transaction.implementation;

import com.example.transaction_manager_service.exception.NotFoundException;
import com.example.transaction_manager_service.exception.RejectedException;
import com.example.transaction_manager_service.model.transaction.entity.Transaction;
import com.example.transaction_manager_service.repository.TransactionRepository;
import com.example.transaction_manager_service.service.transaction.TransactionService;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

@Service
public class TransactionServiceManager implements TransactionService<Transaction> {
    private final TransactionRepository transactionRepository;

    public TransactionServiceManager(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @Override
    public Transaction save(Transaction transaction) {
        if(!transactionRepository.existsById(transaction.getId()) && !transactionRepository.existsByTransaction_id(transaction.getTransaction_id())){
            return transactionRepository.save(transaction);
        } else {
            throw new RejectedException(String.format("can not save existing transaction\ntransaction : %s", transaction));
        }
    }

    @Override
    public Transaction getById(long id) {
        return transactionRepository.findById(id).orElseThrow(() -> new NotFoundException(String.format("transaction is not found by id\nid : %s", id)));
    }

    @Override
    public Transaction getByTransactionId(UUID uuid) {
        return transactionRepository.findTransactionByTransaction_id(uuid).orElseThrow(() -> new NotFoundException(String.format("transaction is not found by transaction id\ntransaction id : %s", uuid)));
    }

    @Override
    public List<Transaction> getBySenderIdAfter(long id, Timestamp afterTime) {
        return transactionRepository.findAllBySenderIdAndTimeAfterOrEqual(id, afterTime);
    }
}
