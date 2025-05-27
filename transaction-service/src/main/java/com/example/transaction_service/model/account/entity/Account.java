package com.example.transaction_service.model.account.entity;

import com.example.transaction_service.model.account.type.entity.AccountType;
import com.example.transaction_service.model.client.entity.Client;
import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * Entity клиентского аккаунта
 */
@Entity
@Table(name = "account")
public class Account implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;
    @ManyToOne
    @JoinColumn(name = "account_type_id")
    private AccountType accountType;
    @Column(name = "balance")
    private double balance;

    public Account(Client client, AccountType accountType) {
        this.client = client;
        this.accountType = accountType;
    }

    public Account() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return Objects.equals(id, account.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", client=" + client +
                ", accountType=" + accountType +
                ", balance=" + balance +
                '}';
    }
}
