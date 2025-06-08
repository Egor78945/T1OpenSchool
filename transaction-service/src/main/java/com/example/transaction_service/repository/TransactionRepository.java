package com.example.transaction_service.repository;

import com.example.transaction_service.model.transaction.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.UUID;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    @Query("select case when exists(from Transaction where transaction_id = :transactionId) then true else false end")
    boolean existsTransactionByTransaction_id(@Param("transactionId") UUID uuid);
    @Query("select count(*) from Transaction t where t.sender.accountId=:senderId and t.time>=:after")
    int countRejectedTransactionsBySenderPerTimeByAccountId(@Param("senderId") UUID senderId, @Param("after") Timestamp after);
    @Query("select count(*) from Transaction t where t.recipient.accountId=:recipientId and t.time>=:after")
    int countRejectedTransactionsByRecipientPerTimeByAccountId(@Param("recipientId") UUID recipientId, @Param("after") Timestamp after);
}
