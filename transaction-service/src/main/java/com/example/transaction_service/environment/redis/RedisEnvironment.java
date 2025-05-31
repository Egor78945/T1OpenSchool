package com.example.transaction_service.environment.redis;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Класс, хранящий переменные окружения, связанные с <b>Redis</b>
 */
@Component
public class RedisEnvironment {
    private final String REDIS_HOST;
    private final int REDIS_PORT;
    private final int REDIS_SUBJECT_LIFE_TIME;

    public RedisEnvironment(@Value("${spring.data.redis.host}") String REDIS_HOST, @Value("${spring.data.redis.port}") int REDIS_PORT, @Value("${spring.data.redis.lifetime}") int REDIS_SUBJECT_LIFE_TIME) {
        this.REDIS_HOST = REDIS_HOST;
        this.REDIS_PORT = REDIS_PORT;
        this.REDIS_SUBJECT_LIFE_TIME = REDIS_SUBJECT_LIFE_TIME;
    }

    public String getREDIS_HOST() {
        return REDIS_HOST;
    }

    public int getREDIS_PORT() {
        return REDIS_PORT;
    }

    public int getREDIS_SUBJECT_LIFE_TIME() {
        return REDIS_SUBJECT_LIFE_TIME;
    }

    public String getREDIS_PASSWORD() {
        return System.getenv("REDIS_PASSWORD");
    }
}
