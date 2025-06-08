package com.example.transaction_service.model.user.dto;

import com.example.transaction_service.service.common.validation.annotation.Letters;
import com.example.transaction_service.service.common.validation.annotation.Password;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.Objects;

public class UserDetailsDTO {
    @NotNull(message = "email is null")
    @NotBlank(message = "email is blank")
    @Email(message = "email is not valid format")
    @Size(min = 15, max = 100, message = "email is out of range")
    private String email;
    @NotNull(message = "password is null")
    @NotBlank(message = "password is blank")
    @Size(min = 10, max = 30, message = "password is out of range")
    @Password
    private String password;
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
    @Size(min = 2, max = 30, message = "patronymic is out of range")
    @Letters(message = "patronymic contains not only letters")
    private String patronymic;

    public UserDetailsDTO(String email, String password, String name, String surname, String patronymic) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.patronymic = patronymic;
    }

    public UserDetailsDTO() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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
        UserDetailsDTO that = (UserDetailsDTO) o;
        return Objects.equals(email, that.email);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(email);
    }

    @Override
    public String toString() {
        return "UserDetailsDTO{" +
                "email='" + email + '\'' +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", patronymic='" + patronymic + '\'' +
                '}';
    }
}
