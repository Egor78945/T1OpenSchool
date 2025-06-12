package com.example.aop_starter.configuration.aop;

import com.example.aop_starter.configuration.common.CommonProperties;
import com.example.aop_starter.configuration.kafka.properties.KafkaProperties;
import com.example.aop_starter.model.log.entity.DatasourceErrorLog;
import com.example.aop_starter.model.log.entity.TimeLimitExceedLog;
import com.example.aop_starter.repository.DatasourceErrorLogRepository;
import com.example.aop_starter.repository.TimeLimitExceedLogRepository;
import com.example.aop_starter.service.common.aop.aspect.cache.CommonServiceCachingAspect;
import com.example.aop_starter.service.common.aop.aspect.log.CommonServiceLoggingAspect;
import com.example.aop_starter.service.common.kafka.producer.router.StarterKafkaProducerServiceRouter;
import com.example.aop_starter.service.common.logging.LoggingService;
import com.example.aop_starter.service.common.logging.implementation.DatasourceErrorLogService;
import com.example.aop_starter.service.common.logging.implementation.TimeLimitExceedLogService;
import com.example.aop_starter.service.common.logging.router.LoggingServiceRouter;
import com.example.aop_starter.service.common.logging.router.implementation.LoggingServiceRouterManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.redis.core.RedisTemplate;

@Configuration
@EnableConfigurationProperties(CommonProperties.class)
@ConditionalOnClass(JpaRepository.class)
@EnableJpaRepositories(basePackages = "com.example.aop_starter.repository")
@AutoConfigureAfter(DataSourceAutoConfiguration.class)
public class AOPConfiguration {
    @Bean
    @ConditionalOnMissingBean
    public LoggingService<DatasourceErrorLog> datasourceErrorLogLoggingService(DatasourceErrorLogRepository datasourceErrorLogRepository){
        return new DatasourceErrorLogService(datasourceErrorLogRepository);
    }

    @Bean
    @ConditionalOnMissingBean
    public LoggingService<TimeLimitExceedLog> timeLimitExceedLogLoggingService(TimeLimitExceedLogRepository timeLimitExceedLogRepository){
        return new TimeLimitExceedLogService(timeLimitExceedLogRepository);
    }

    @Bean
    @ConditionalOnMissingBean
    public LoggingServiceRouter loggingServiceRouter(@Qualifier("datasourceErrorLogLoggingService") LoggingService<DatasourceErrorLog> datasourceErrorLogLoggingService, @Qualifier("timeLimitExceedLogLoggingService") LoggingService<TimeLimitExceedLog> timeLimitExceedLogLoggingService){
        return new LoggingServiceRouterManager((DatasourceErrorLogService) datasourceErrorLogLoggingService, (TimeLimitExceedLogService) timeLimitExceedLogLoggingService);
    }

    @Bean
    @ConditionalOnMissingBean
    public CommonServiceCachingAspect commonServiceCachingAspect(RedisTemplate<String, Object> cacheService){
        return new CommonServiceCachingAspect(cacheService);
    }

    @Bean
    @ConditionalOnMissingBean
    public CommonServiceLoggingAspect commonServiceLoggingAspect(LoggingServiceRouter loggingServiceRouter, StarterKafkaProducerServiceRouter starterKafkaProducerServiceRouter, CommonProperties commonProperties, KafkaProperties kafkaProperties){
        return new CommonServiceLoggingAspect(loggingServiceRouter, starterKafkaProducerServiceRouter, commonProperties, kafkaProperties);
    }
}
