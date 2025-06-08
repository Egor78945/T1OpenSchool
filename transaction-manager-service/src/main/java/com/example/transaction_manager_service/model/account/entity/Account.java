package com.example.transaction_manager_service.model.account.entity;

import com.example.transaction_manager_service.model.account.status.entity.AccountStatus;
import com.example.transaction_manager_service.model.account.type.entity.AccountType;
import com.example.transaction_manager_service.model.client.entity.Client;
import jakarta.persistence.*;
import org.hibernate.annotations.NaturalId;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

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
    @Column(name = "account_id")
    @NaturalId
    private UUID accountId;
    @ManyToOne
    @JoinColumn(name = "client_id", referencedColumnName = "client_id")
    private Client client;
    @ManyToOne
    @JoinColumn(name = "account_type_id")
    private AccountType accountType;
    @ManyToOne
    @JoinColumn(name = "account_status_id")
    private AccountStatus accountStatus;
    @Column(name = "balance")
    private double balance;
    @Column(name = "frozen_balance")
    private double frozen_balance;

    public Account(Client client, UUID accountId, AccountType accountType, AccountStatus accountStatus) {
        this.client = client;
        this.accountId = accountId;
        this.accountType = accountType;
        this.accountStatus = accountStatus;
    }

    public Account() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UUID getAccountId() {
        return accountId;
    }

    public void setAccountId(UUID accountId) {
        this.accountId = accountId;
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

    public AccountStatus getAccountStatus() {
        return accountStatus;
    }

    public void setAccountStatus(AccountStatus accountStatus) {
        this.accountStatus = accountStatus;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public double getFrozen_balance() {
        return frozen_balance;
    }

    public void setFrozen_balance(double frozen_balance) {
        this.frozen_balance = frozen_balance;
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
                ", accountId=" + accountId +
                ", client=" + client +
                ", accountType=" + accountType +
                ", accountStatus=" + accountStatus +
                ", balance=" + balance +
                ", frozen_balance=" + frozen_balance +
                '}';
    }
}
