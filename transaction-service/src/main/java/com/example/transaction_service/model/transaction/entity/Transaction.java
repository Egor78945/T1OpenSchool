package com.example.transaction_service.model.transaction.entity;

import com.example.transaction_service.model.account.entity.Account;
import com.example.transaction_service.model.transaction.type.entity.TransactionType;
import jakarta.persistence.*;

import java.sql.Timestamp;
import java.util.Objects;

/**
 * Entity транзакций по клиентских аккаунтам
 */
@Entity
@Table(name = "transaction")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @ManyToOne
    @JoinColumn(name = "account_sender_id")
    private Account sender;
    @ManyToOne
    @JoinColumn(name = "account_recipient_id")
    private Account recipient;
    @ManyToOne
    @JoinColumn(name = "transaction_type_id")
    private TransactionType transactionType;
    @Column(name = "amount")
    private double amount;
    @Column(name = "time")
    private Timestamp time;

    public Transaction(Account sender, Account recipient, TransactionType transactionType, double amount, Timestamp time) {
        this.sender = sender;
        this.recipient = recipient;
        this.transactionType = transactionType;
        this.amount = amount;
        this.time = time;
    }

    public Transaction() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Account getSender() {
        return sender;
    }

    public void setSender(Account sender) {
        this.sender = sender;
    }

    public Account getRecipient() {
        return recipient;
    }

    public void setRecipient(Account recipient) {
        this.recipient = recipient;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Transaction that = (Transaction) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + id +
                ", sender=" + sender +
                ", recipient=" + recipient +
                ", transactionType=" + transactionType +
                ", amount=" + amount +
                ", time=" + time +
                '}';
    }
}




