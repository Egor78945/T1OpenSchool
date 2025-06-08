package com.example.transaction_manager_service.model.client.entity;

import com.example.transaction_manager_service.model.client.status.entity.ClientStatus;
import com.example.transaction_manager_service.model.user.entity.User;
import jakarta.persistence.*;
import org.hibernate.annotations.NaturalId;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "client")
public class Client implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @NaturalId
    @Column(name = "client_id")
    private UUID client_id;
    @Column(name = "name")
    private String name;
    @Column(name = "surname")
    private String surname;
    @Column(name = "patronymic")
    private String patronymic;
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user_id;
    @ManyToOne
    @JoinColumn(name = "client_status_id")
    private ClientStatus client_status_id;

    public Client(String name, String surname, String patronymic, User user, ClientStatus clientStatus) {
        this.name = name;
        this.surname = surname;
        this.patronymic = patronymic;
        this.user_id = user;
        this.client_status_id = clientStatus;
    }

    public Client() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UUID getClient_id() {
        return client_id;
    }

    public void setClient_id(UUID client_id) {
        this.client_id = client_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public User getUser_id() {
        return user_id;
    }

    public void setUser_id(User user_id) {
        this.user_id = user_id;
    }

    public ClientStatus getClient_status_id() {
        return client_status_id;
    }

    public void setClient_status_id(ClientStatus client_status_id) {
        this.client_status_id = client_status_id;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Client client = (Client) o;
        return Objects.equals(id, client.id) && Objects.equals(client_id, client.client_id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, client_id);
    }

    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", client_id=" + client_id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", patronymic='" + patronymic + '\'' +
                ", user_id=" + user_id +
                ", client_status_id=" + client_status_id +
                '}';
    }
}