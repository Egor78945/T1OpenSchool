package com.example.transaction_service.service.common.cache;

/**
 * Сервис, предоставляющий функционал для управления кэшем
 */
public abstract class AbstractCacheService {
    /**
     * Получить объект из кэша по его ключю
     * @param key Ключ кэшируемого объекта
     * @return Кэшируемый объект
     * @param <K> Тип ключа кэшируемого объекта
     * @param <O> Тип кэщируемого объекта
     */
    public abstract <K, O> O get(K key);

    /**
     * Проверить, есть ли в кэше объект по ключю
     * @param key Ключ кэшируемого объекта
     * @return Результат проверки
     * @param <K> Тип ключа кэщируемого объекта
     */
    public abstract <K> boolean exists(K key);
}