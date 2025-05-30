package com.example.transaction_service.service.common.aop.aspect.log;

import com.example.transaction_service.environment.common.CommonEnvironment;
import com.example.transaction_service.environment.kafka.KafkaEnvironment;
import com.example.transaction_service.exception.NotFoundException;
import com.example.transaction_service.model.common.error.enumeration.ErrorEnumeration;
import com.example.transaction_service.model.log.entity.DatasourceErrorLog;
import com.example.transaction_service.model.log.entity.TimeLimitExceedLog;
import com.example.transaction_service.service.common.aop.annotation.LogDatasourceError;
import com.example.transaction_service.service.common.aop.annotation.Metric;
import com.example.transaction_service.service.kafka.producer.router.KafkaProducerServiceRouter;
import com.example.transaction_service.service.logging.router.LoggingServiceRouter;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.header.internals.RecordHeader;
import org.apache.kafka.common.header.internals.RecordHeaders;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

/**
 * Общий класс-аспект, выполняющий действия с логгированием
 */
@Component
@Aspect
public class CommonServiceLoggingAspect {
    private final CommonEnvironment commonEnvironment;
    private final LoggingServiceRouter loggingServiceRouter;
    private final KafkaProducerServiceRouter kafkaProducerServiceRouter;
    private final KafkaEnvironment kafkaEnvironment;

    public CommonServiceLoggingAspect(@Qualifier("loggingServiceRouterManager") LoggingServiceRouter loggingServiceRouter, @Qualifier("kafkaProducerServiceRouterManager") KafkaProducerServiceRouter kafkaProducerServiceRouter, CommonEnvironment commonEnvironment, KafkaEnvironment kafkaEnvironment) {
        this.commonEnvironment = commonEnvironment;
        this.kafkaEnvironment = kafkaEnvironment;
        this.loggingServiceRouter = loggingServiceRouter;
        this.kafkaProducerServiceRouter = kafkaProducerServiceRouter;
    }

    /**
     * Advice, отлавливающий исключения из методов, помеченных аннотацией {@link LogDatasourceError}
     *
     * @param e Выброшенное методом исключение
     */
    @AfterThrowing(pointcut = "@annotation(com.example.transaction_service.service.common.aop.annotation.LogDatasourceError)", throwing = "e")
    public void logAfterMethodThrowing(Exception e) {
        DatasourceErrorLog datasourceErrorLog = new DatasourceErrorLog(e.getClass().getSimpleName(), Arrays.toString(e.getStackTrace()), e.getMessage(), Timestamp.valueOf(LocalDateTime.now()));
        try {
            kafkaProducerServiceRouter.getKafkaProducerService(DatasourceErrorLog.class)
                    .orElseThrow(() -> new NotFoundException(String.format("kafka service is not found by class\nClass : %s", DatasourceErrorLog.class)))
                    .send(new ProducerRecord<>(kafkaEnvironment.getKAFKA_TOPIC_METRIC_NAME(), null, "datasource error", datasourceErrorLog, new RecordHeaders(List.of(new RecordHeader("error type", ErrorEnumeration.DATA_SOURCE.name().getBytes())))));
        } catch (Exception exception) {
            loggingServiceRouter.getByTargetClass(DatasourceErrorLog.class).orElseThrow(() -> new NotFoundException(String.format("logging service is not found by class\nClass : %s", DatasourceErrorLog.class))).log(datasourceErrorLog);
        }
    }

    /**
     * Advice, отслеживающий время выполнения методов, помеченных с помощью аннотации {@link Metric}
     * и сохраняющий сообщение об превышении времени выполнения, если оно было
     *
     * @return Оригинальный результат метода, помеченного аннотацией {@link Metric}
     * @throws Throwable возможное исключение метода, помеченного аннотацией {@link Metric}
     */
    @Around("@annotation(com.example.transaction_service.service.common.aop.annotation.Metric)")
    public Object logAfterMethodRuntimeIsExpired(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();
        try {
            return joinPoint.proceed();
        } finally {
            long end = System.currentTimeMillis();
            if (end - start > commonEnvironment.getMETHOD_RUNTIME_LIMIT()) {
                TimeLimitExceedLog timeLimitExceedLog = new TimeLimitExceedLog(joinPoint.getSignature().getDeclaringTypeName(), joinPoint.getSignature().getName(), end - start, Timestamp.valueOf(LocalDateTime.now()));
                try {
                    kafkaProducerServiceRouter.getKafkaProducerService(TimeLimitExceedLog.class)
                            .orElseThrow(() -> new NotFoundException(String.format("kafka service is not found by class\nClass : %s", DatasourceErrorLog.class)))
                            .send(new ProducerRecord<>(kafkaEnvironment.getKAFKA_TOPIC_METRIC_NAME(), null, "time limit exceed error", timeLimitExceedLog, new RecordHeaders(List.of(new RecordHeader("error type", ErrorEnumeration.METRICS.name().getBytes())))));
                } catch (Exception e) {
                    loggingServiceRouter.getByTargetClass(TimeLimitExceedLog.class).orElseThrow(() -> new NotFoundException(String.format("logging service is not found by class\nClass : %s", TimeLimitExceedLog.class))).log(timeLimitExceedLog);
                }
            }
        }
    }
}
