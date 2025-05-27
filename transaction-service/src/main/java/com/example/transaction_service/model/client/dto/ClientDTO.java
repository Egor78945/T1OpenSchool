package com.example.transaction_service.model.client.dto;

import com.example.transaction_service.service.common.validation.annotation.Letters;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.Objects;

/**
 * Класс, представляющий собой DTO основных клиентских полей
 */
public class ClientDTO {
    @NotNull(message = "name is null")
    @NotBlank(message = "name is blank")
    @Size(min = 2, max = 15, message = "name is out of range")
    @Letters(message = "name contains not only letters")
    private String name;
    @NotNull(message = "surname is null")
    @NotBlank(message = "surname is blank")
    @Size(min = 2, max = 15, message = "surname is out of range")
    @Letters(message = "surname contains not only letters")
    private String surname;
    @NotNull(message = "patronymic is null")
    @NotBlank(message = "patronymic is blank")
    @Size(min = 2, max = 15, message = "patronymic is out of range")
    @Letters(message = "patronymic contains not only letters")
    private String patronymic;

    public ClientDTO(String name, String surname, String patronymic) {
        this.name = name;
        this.surname = surname;
        this.patronymic = patronymic;
    }

    public ClientDTO() {
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

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        ClientDTO that = (ClientDTO) o;
        return Objects.equals(name, that.name) && Objects.equals(surname, that.surname) && Objects.equals(patronymic, that.patronymic);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, surname, patronymic);
    }

    @Override
    public String toString() {
        return "ClientRegistrationDTO{" +
                "name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", patronymic='" + patronymic + '\'' +
                '}';
    }
}
