package com.example.transaction_manager_service.model.account.status.entity;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * Entity статуса клиентского аккаунта
 */
@Entity
@Table(name = "account_status")
public class AccountStatus implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "name")
    private String name;

    public AccountStatus(String name) {
        this.name = name;
    }

    public AccountStatus() {
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
        AccountStatus that = (AccountStatus) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "AccountStatus{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
