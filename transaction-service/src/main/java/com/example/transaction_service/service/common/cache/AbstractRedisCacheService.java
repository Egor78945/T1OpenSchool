package com.example.transaction_service.service.common.cache;

/**
 * Абстрактная реализация сервиса по работе с кэшем для работы с <b>Redis</b>
 */
public abstract class AbstractRedisCacheService extends AbstractCacheService {
    @Override
    public abstract <K, O> O get(K key);

    public abstract <HK, K, O> O getFromHash(HK hashKey, K key);

    public abstract <HK, K, O> void saveToHash(HK hashKey, K key, O value);

    public abstract <K, O> O getFromList(K key);

    public abstract <O> void saveToList(O key);

    @Override
    public abstract <K> boolean exists(K key);
}
