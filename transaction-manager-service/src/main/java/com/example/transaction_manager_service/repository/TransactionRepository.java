package com.example.transaction_manager_service.repository;

import com.example.transaction_manager_service.model.transaction.entity.Transaction;
import org.apache.kafka.clients.producer.internals.Sender;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    @Query("from Transaction t where t.sender=:sender and t.time>=:after")
    List<Transaction> findAllBySenderAndTimeAfterOrEqual(@Param("sender") Sender sender, @Param("after") Timestamp after);
}
