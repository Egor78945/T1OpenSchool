package com.example.transaction_manager_service.model.transaction.entity;

import com.example.transaction_service.model.account.entity.Account;
import com.example.transaction_service.model.transaction.status.entity.TransactionStatus;
import com.example.transaction_service.model.transaction.type.entity.TransactionType;
import jakarta.persistence.*;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Objects;
import java.util.UUID;

/**
 * Entity транзакций по клиентских аккаунтам
 */
@Entity
@Table(name = "transaction")
public class Transaction implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "transaction_id")
    private UUID transaction_id;
    @ManyToOne
    @JoinColumn(name = "account_sender_id")
    private Account sender;
    @ManyToOne
    @JoinColumn(name = "account_recipient_id")
    private Account recipient;
    @ManyToOne
    @JoinColumn(name = "transaction_type_id")
    private TransactionType transactionType;
    @ManyToOne
    @JoinColumn(name = "transaction_status_id")
    private TransactionStatus transactionStatus;
    @Column(name = "amount")
    private double amount;
    @Column(name = "time")
    private Timestamp time;

    public Transaction(UUID transaction_id, Account sender, Account recipient, TransactionType transactionType, TransactionStatus transactionStatus, double amount, Timestamp time) {
        this.transaction_id = transaction_id;
        this.sender = sender;
        this.recipient = recipient;
        this.transactionType = transactionType;
        this.transactionStatus = transactionStatus;
        this.amount = amount;
        this.time = time;
    }

    public Transaction(Builder builder) {
        this.transaction_id = builder.transaction_id;
        this.sender = builder.sender;
        this.recipient = builder.recipient;
        this.transactionType = builder.transactionType;
        this.transactionStatus = builder.transactionStatus;
        this.amount = builder.amount;
        this.time = builder.time;
    }

    public Transaction() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UUID getTransaction_id() {
        return transaction_id;
    }

    public void setTransaction_id(UUID transaction_id) {
        this.transaction_id = transaction_id;
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

    public TransactionStatus getTransactionStatus() {
        return transactionStatus;
    }

    public void setTransactionStatus(TransactionStatus transactionStatus) {
        this.transactionStatus = transactionStatus;
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
                ", transaction_id=" + transaction_id +
                ", sender=" + sender +
                ", recipient=" + recipient +
                ", transactionType=" + transactionType +
                ", transactionStatus=" + transactionStatus +
                ", amount=" + amount +
                ", time=" + time +
                '}';
    }

    public static Builder builder(){
        return new Builder();
    }

    public static class Builder {
        private UUID transaction_id;
        private Account sender;
        private Account recipient;
        private TransactionType transactionType;
        private TransactionStatus transactionStatus;
        private Double amount;
        private Timestamp time;

        public Builder setTransactionId(UUID transaction_id) {
            this.transaction_id = transaction_id;
            return this;
        }

        public Builder setSender(Account sender) {
            this.sender = sender;
            return this;
        }

        public Builder setRecipient(Account recipient) {
            this.recipient = recipient;
            return this;
        }

        public Builder setTransactionType(TransactionType transactionType) {
            this.transactionType = transactionType;
            return this;
        }

        public Builder setTransactionStatus(TransactionStatus transactionStatus) {
            this.transactionStatus = transactionStatus;
            return this;
        }

        public Builder setAmount(double amount) {
            this.amount = amount;
            return this;
        }

        public Builder setTime(Timestamp time) {
            this.time = time;
            return this;
        }

        public Transaction build() {
            if (transaction_id != null && sender != null && recipient != null && transactionType != null && transactionStatus != null && amount != null && time != null) {
                return new Transaction(this);
            }
            throw new NullPointerException("not all fields is initialized");
        }
    }
}
