package com.example.blocking_service.model.client.dto;

import java.util.List;
import java.util.Objects;

public class ClientIdListDTO {
    private List<String> clientIdList;

    public ClientIdListDTO(List<String> clientIdList) {
        this.clientIdList = clientIdList;
    }

    public ClientIdListDTO() {
    }

    public List<String> getClientIdList() {
        return clientIdList;
    }

    public void setClientIdList(List<String> clientIdList) {
        this.clientIdList = clientIdList;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        ClientIdListDTO that = (ClientIdListDTO) o;
        return Objects.equals(clientIdList, that.clientIdList);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(clientIdList);
    }

    @Override
    public String toString() {
        return "ClientIdListDTO{" +
                "clientIdList=" + clientIdList +
                '}';
    }
}
