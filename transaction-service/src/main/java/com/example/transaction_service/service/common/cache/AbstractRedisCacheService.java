package com.example.transaction_service.service.common.cache;

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
