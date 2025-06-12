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
import com.example.aop_starter.service.common.logging.StarterLoggingService;
import com.example.aop_starter.service.common.logging.implementation.DatasourceErrorLogServiceStarter;
import com.example.aop_starter.service.common.logging.implementation.TimeLimitExceedLogServiceStarter;
import com.example.aop_starter.service.common.logging.router.StarterLoggingServiceRouter;
import com.example.aop_starter.service.common.logging.router.implementation.StarterLoggingServiceRouterManager;
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
    public StarterLoggingService<DatasourceErrorLog> datasourceErrorLogLoggingService(DatasourceErrorLogRepository datasourceErrorLogRepository){
        return new DatasourceErrorLogServiceStarter(datasourceErrorLogRepository);
    }

    @Bean
    @ConditionalOnMissingBean
    public StarterLoggingService<TimeLimitExceedLog> timeLimitExceedLogLoggingService(TimeLimitExceedLogRepository timeLimitExceedLogRepository){
        return new TimeLimitExceedLogServiceStarter(timeLimitExceedLogRepository);
    }

    @Bean
    @ConditionalOnMissingBean
    public StarterLoggingServiceRouter loggingServiceRouter(@Qualifier("datasourceErrorLogLoggingService") StarterLoggingService<DatasourceErrorLog> datasourceErrorLogStarterLoggingService, @Qualifier("timeLimitExceedLogLoggingService") StarterLoggingService<TimeLimitExceedLog> timeLimitExceedLogStarterLoggingService){
        return new StarterLoggingServiceRouterManager((DatasourceErrorLogServiceStarter) datasourceErrorLogStarterLoggingService, (TimeLimitExceedLogServiceStarter) timeLimitExceedLogStarterLoggingService);
    }

    @Bean
    @ConditionalOnMissingBean
    public CommonServiceCachingAspect commonServiceCachingAspect(RedisTemplate<String, Object> cacheService){
        return new CommonServiceCachingAspect(cacheService);
    }

    @Bean
    @ConditionalOnMissingBean
    public CommonServiceLoggingAspect commonServiceLoggingAspect(StarterLoggingServiceRouter starterLoggingServiceRouter, StarterKafkaProducerServiceRouter starterKafkaProducerServiceRouter, CommonProperties commonProperties, KafkaProperties kafkaProperties){
        return new CommonServiceLoggingAspect(starterLoggingServiceRouter, starterKafkaProducerServiceRouter, commonProperties, kafkaProperties);
    }
}
