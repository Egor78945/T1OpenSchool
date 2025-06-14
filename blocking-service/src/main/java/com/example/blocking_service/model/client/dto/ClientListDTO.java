package com.example.blocking_service.model.client.dto;

import com.example.blocking_service.model.client.entity.Client;

import java.util.List;
import java.util.Objects;

public class ClientListDTO {
    private List<Client> clients;

    public ClientListDTO(List<Client> clients) {
        this.clients = clients;
    }

    public ClientListDTO() {
    }

    public List<Client> getClients() {
        return clients;
    }

    public void setClients(List<Client> clients) {
        this.clients = clients;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        ClientListDTO that = (ClientListDTO) o;
        return Objects.equals(clients, that.clients);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(clients);
    }

    @Override
    public String toString() {
        return "ClientListDTO{" +
                "clients=" + clients +
                '}';
    }
}
