package com.example.transaction_service.configuration.redis;

import com.example.transaction_service.environment.redis.RedisEnvironment;
import com.fasterxml.jackson.databind.json.JsonMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;

import java.time.Duration;

/**
 * Конфигурация Redis
 */
@Configuration
public class RedisConfiguration {
    protected final RedisEnvironment redisEnvironment;

    public RedisConfiguration(RedisEnvironment redisEnvironment) {
        this.redisEnvironment = redisEnvironment;
    }

    @Bean
    public RedisCacheManager redisCacheManager(RedisConnectionFactory connectionFactory) {
        RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofMillis(redisEnvironment.getREDIS_SUBJECT_LIFE_TIME()))
                .disableCachingNullValues();

        return RedisCacheManager.builder(connectionFactory)
                .cacheDefaults(redisCacheConfiguration)
                .build();
    }

    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration(redisEnvironment.getREDIS_HOST(), redisEnvironment.getREDIS_PORT());
        redisStandaloneConfiguration.setPassword(RedisPassword.of(redisEnvironment.getREDIS_PASSWORD()));
        return new LettuceConnectionFactory(redisStandaloneConfiguration);
    }

    @Bean
    public JsonMapper jsonMapper(){
        return new JsonMapper();
    }
}
