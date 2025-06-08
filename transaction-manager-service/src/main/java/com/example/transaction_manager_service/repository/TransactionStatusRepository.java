package com.example.transaction_manager_service.repository;

import com.example.transaction_manager_service.model.transaction.status.entity.TransactionStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionStatusRepository extends JpaRepository<TransactionStatus, Long> {
}
