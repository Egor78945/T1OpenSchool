package com.example.blocking_service.model.account.dto;

import com.example.blocking_service.model.account.entity.Account;

import java.util.List;
import java.util.Objects;

public class AccountListDTO {
    private List<Account> accounts;

    public AccountListDTO(List<Account> accounts) {
        this.accounts = accounts;
    }

    public AccountListDTO() {
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        AccountListDTO that = (AccountListDTO) o;
        return Objects.equals(accounts, that.accounts);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(accounts);
    }

    @Override
    public String toString() {
        return "AccountListDTO{" +
                "accounts=" + accounts +
                '}';
    }
}
