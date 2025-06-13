package com.example.aop_starter.configuration.aop;

import com.example.aop_starter.configuration.common.CommonProperties;
import com.example.aop_starter.configuration.kafka.properties.KafkaProperties;
import com.example.aop_starter.repository.DatasourceErrorLogRepository;
import com.example.aop_starter.repository.TimeLimitExceedLogRepository;
import com.example.aop_starter.service.common.aop.aspect.cache.CommonServiceCachingAspect;
import com.example.aop_starter.service.common.aop.aspect.log.CommonServiceLoggingAspect;
import com.example.aop_starter.service.common.kafka.producer.router.StarterKafkaProducerServiceRouter;
import com.example.aop_starter.service.common.logging.implementation.DatasourceErrorLogServiceStarter;
import com.example.aop_starter.service.common.logging.implementation.TimeLimitExceedLogServiceStarter;
import com.example.aop_starter.service.common.logging.router.implementation.StarterLoggingServiceRouterManager;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

@Configuration
@EnableConfigurationProperties(CommonProperties.class)
public class AOPConfiguration {
    @Bean
    public DatasourceErrorLogServiceStarter datasourceErrorLogLoggingService(DatasourceErrorLogRepository datasourceErrorLogRepository) {
        return new DatasourceErrorLogServiceStarter(datasourceErrorLogRepository);
    }

    @Bean
    public TimeLimitExceedLogServiceStarter timeLimitExceedLogLoggingService(TimeLimitExceedLogRepository timeLimitExceedLogRepository) {
        return new TimeLimitExceedLogServiceStarter(timeLimitExceedLogRepository);
    }

    @Bean
    public StarterLoggingServiceRouterManager loggingServiceRouter(DatasourceErrorLogServiceStarter datasourceErrorLogStarterLoggingService, TimeLimitExceedLogServiceStarter timeLimitExceedLogStarterLoggingService) {
        return new StarterLoggingServiceRouterManager(datasourceErrorLogStarterLoggingService, timeLimitExceedLogStarterLoggingService);
    }

    @Bean
    public CommonServiceCachingAspect commonServiceCachingAspect(RedisTemplate<String, Object> cacheService) {
        return new CommonServiceCachingAspect(cacheService);
    }

    @Bean
    public CommonServiceLoggingAspect commonServiceLoggingAspect(StarterLoggingServiceRouterManager starterLoggingServiceRouter, StarterKafkaProducerServiceRouter starterKafkaProducerServiceRouter, CommonProperties commonProperties, KafkaProperties kafkaProperties) {
        return new CommonServiceLoggingAspect(starterLoggingServiceRouter, starterKafkaProducerServiceRouter, commonProperties, kafkaProperties);
    }
}
