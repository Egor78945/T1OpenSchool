package com.example.transaction_manager_service.repository;

import com.example.transaction_manager_service.model.transaction.entity.Transaction;
import org.apache.kafka.clients.producer.internals.Sender;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    @Query("from Transaction t where t.sender.id=:senderId and t.time>=:after")
    List<Transaction> findAllBySenderIdAndTimeAfterOrEqual(@Param("senderId") long id, @Param("after") Timestamp after);

    @Query("select case when exists(from Transaction t where t.transaction_id=:transactionId) then true else false end")
    boolean existsByTransaction_id(@Param("transactionId") UUID uuid);

    @Query("from Transaction t where t.transaction_id=:transactionId")
    Optional<Transaction> findTransactionByTransaction_id(@Param("transactionId") UUID uuid);
}
