package com.example.transaction_manager_service.model.transaction.status.entity;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * Entity статуса транзакции
 */
@Entity
@Table(name = "transaction_status")
public class TransactionStatus implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name")
    private String name;

    public TransactionStatus(String name) {
        this.name = name;
    }

    public TransactionStatus() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        TransactionStatus that = (TransactionStatus) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "TransactionStatus{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
