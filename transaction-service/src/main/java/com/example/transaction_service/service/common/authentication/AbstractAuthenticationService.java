package com.example.transaction_service.service.common.authentication;

/**
 * Абстрактный класс, предоставляющий функционал для аутентификации
 * @param <R> Тип, представляющий <b>DTO</b> с данными для регистрации
 */
public abstract class AbstractAuthenticationService <R> {
    /**
     * Метод, выполняющий аутентификацию
     * @param username имя объекта
     * @param password пароль объекта
     * @return Токен аутентификации
     */
    public abstract String login(String username, String password);

    /**
     * Выполнить регистрацию
     * @param registrationModel DTO, хранящий основную информацию для проведения регистрации
     */
    public abstract R registration(R registrationModel);
}
