package com.example.aop_starter.service.common.aop.aspect.log;

import com.example.aop_starter.configuration.common.CommonProperties;
import com.example.aop_starter.configuration.kafka.properties.KafkaProperties;
import com.example.aop_starter.exception.ObjectNotFoundException;
import com.example.aop_starter.model.common.error.enumeration.ErrorTopicEnumeration;
import com.example.aop_starter.model.log.entity.DatasourceErrorLog;
import com.example.aop_starter.model.log.entity.TimeLimitExceedLog;
import com.example.aop_starter.service.common.kafka.producer.router.StarterKafkaProducerServiceRouter;
import com.example.aop_starter.service.common.logging.router.LoggingServiceRouter;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.header.internals.RecordHeader;
import org.apache.kafka.common.header.internals.RecordHeaders;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Aspect
public class CommonServiceLoggingAspect {
    private final CommonProperties commonProperties;
    private final LoggingServiceRouter loggingServiceRouter;
    private final StarterKafkaProducerServiceRouter starterKafkaProducerServiceRouter;
    private final KafkaProperties kafkaProperties;

    public CommonServiceLoggingAspect(LoggingServiceRouter loggingServiceRouter, StarterKafkaProducerServiceRouter starterKafkaProducerServiceRouter, CommonProperties commonProperties, KafkaProperties kafkaProperties) {
        this.commonProperties = commonProperties;
        this.kafkaProperties = kafkaProperties;
        this.loggingServiceRouter = loggingServiceRouter;
        this.starterKafkaProducerServiceRouter = starterKafkaProducerServiceRouter;
    }

    @AfterThrowing(pointcut = "@annotation(com.example.aop_starter.service.common.aop.annotation.LogDatasourceError)", throwing = "e")
    public void logAfterMethodThrowing(Exception e) {
        DatasourceErrorLog datasourceErrorLog = new DatasourceErrorLog(e.getClass().getSimpleName(), Arrays.toString(e.getStackTrace()), e.getMessage(), Timestamp.valueOf(LocalDateTime.now()));
        try {
            starterKafkaProducerServiceRouter.getKafkaProducerService(DatasourceErrorLog.class)
                    .send(new ProducerRecord<>(kafkaProperties.getMetric().getName(), null, datasourceErrorLog.getTime().toString(), datasourceErrorLog, new RecordHeaders(List.of(new RecordHeader("error type", ErrorTopicEnumeration.DATA_SOURCE.name().getBytes())))));
        } catch (Exception exception) {
            loggingServiceRouter.getByTargetClass(DatasourceErrorLog.class).orElseThrow(() -> new ObjectNotFoundException(String.format("logging service is not found by class\nClass : %s", DatasourceErrorLog.class))).log(datasourceErrorLog);
        }
    }

    @Around("@annotation(com.example.aop_starter.service.common.aop.annotation.Metric)")
    public Object logAfterMethodRuntimeIsExpired(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();
        try {
            return joinPoint.proceed();
        } finally {
            long end = System.currentTimeMillis();
            if (end - start > commonProperties.getRuntimeLimit()) {
                TimeLimitExceedLog timeLimitExceedLog = new TimeLimitExceedLog(joinPoint.getSignature().getDeclaringTypeName(), joinPoint.getSignature().getName(), end - start, Timestamp.valueOf(LocalDateTime.now()));
                try {
                    starterKafkaProducerServiceRouter.getKafkaProducerService(TimeLimitExceedLog.class)
                            .send(new ProducerRecord<>(kafkaProperties.getMetric().getName(), null, timeLimitExceedLog.getTime().toString(), timeLimitExceedLog, new RecordHeaders(List.of(new RecordHeader("error type", ErrorTopicEnumeration.METRICS.name().getBytes())))));
                } catch (Exception e) {
                    loggingServiceRouter.getByTargetClass(TimeLimitExceedLog.class).orElseThrow(() -> new ObjectNotFoundException(String.format("logging service is not found by class\nClass : %s", TimeLimitExceedLog.class))).log(timeLimitExceedLog);
                }
            }
        }
    }
}
