package com.example.transaction_service.service.common.cache;

public abstract class AbstractCacheService {
    public abstract <K, O> O get(K key);
    public abstract <K> boolean exists(K key);
}