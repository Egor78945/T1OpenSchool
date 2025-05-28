package com.example.transaction_service.service.common.authentication;

/**
 * Абстрактный класс, предоставляющий функционал для аутентификации
 * @param <L> Тип, представляющий <b>DTO</b> с данными для авторизации
 * @param <R> Тип, представляющий <b>DTO</b> с данными для регистрации
 */
public abstract class AbstractAuthenticationService <L, R> {
    /**
     * Выполнить авторизацию
     * @param loginModel DTO, хранящий основную информацию для проведения авторизации
     * @return Аутентификационный токен
     */
    public abstract String login(L loginModel);

    /**
     * Выполнить регистрацию
     * @param registrationModel DTO, хранящий основную информацию для проведения регистрации
     */
    public abstract String registration(R registrationModel);
}
