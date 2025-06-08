package com.example.transaction_service.model.client.dto;

import java.util.Objects;

public class ClientAccountDTO {
    private String clientId;
    private String accountId;

    public ClientAccountDTO(String clientId, String accountId) {
        this.clientId = clientId;
        this.accountId = accountId;
    }

    public ClientAccountDTO() {
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        ClientAccountDTO that = (ClientAccountDTO) o;
        return Objects.equals(clientId, that.clientId) && Objects.equals(accountId, that.accountId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(clientId, accountId);
    }

    @Override
    public String toString() {
        return "{" +
                "clientId='" + clientId + '\'' +
                ", accountId='" + accountId + '\'' +
                '}';
    }
}
