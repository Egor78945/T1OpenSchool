package com.example.transaction_service.service.common.aop.aspect.cache;

import com.example.transaction_service.exception.ProcessingException;
import com.example.transaction_service.service.common.aop.annotation.Cached;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * Общий класс-аспект, выполняющий действия с кэшированием часто используемых данных
 */
@Component
@Aspect
public class CommonServiceCachingAspect {
    private final RedisTemplate<String, Object> cacheService;

    public CommonServiceCachingAspect(RedisTemplate<String, Object> cacheService) {
        this.cacheService = cacheService;
    }

    @Around("@annotation(cached)")
    public Object getCachedData(ProceedingJoinPoint joinPoint, Cached cached) throws Throwable {
        String cacheKey = cached.name().isEmpty() ? ((MethodSignature) joinPoint.getSignature()).getReturnType().getSimpleName() : cached.name();
        String hashKey = buildHashKey(joinPoint);
        Object cachedObject = cacheService.opsForHash().get(cacheKey, hashKey);
        if (cachedObject != null) {
            return cachedObject;
        }
        Object originalObject = joinPoint.proceed();
        if (!(originalObject instanceof Serializable)) {
            throw new ProcessingException(String.format("cacheable object is not serializable\nObject : %s", originalObject.getClass().getSimpleName()));
        }
        cacheService.opsForHash().put(cacheKey, hashKey, originalObject);
        return originalObject;
    }

    private String buildHashKey(JoinPoint joinPoint) {
        StringBuilder sb = new StringBuilder();
        sb.append(joinPoint.getSignature().toShortString());
        for (Object sub : joinPoint.getArgs()) {
            String subString = sub == null ? "null" : sub.toString();
            sb.append(subString);
        }
        return sb.toString();
    }
}
