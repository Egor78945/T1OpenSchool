package com.example.transaction_service.service.common.aop.aspect.log;

import com.example.transaction_service.environment.common.CommonEnvironment;
import com.example.transaction_service.model.log.entity.DatasourceErrorLog;
import com.example.transaction_service.model.log.entity.TimeLimitExceedLog;
import com.example.transaction_service.service.common.aop.annotation.Metric;
import com.example.transaction_service.service.logging.LoggingService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Arrays;

import com.example.transaction_service.service.common.aop.annotation.LogDatasourceError;

/**
 * Общий класс-аспект, выполняющий действия с логгированием
 */
@Component
@Aspect
public class CommonServiceLoggingAspect {
    private final CommonEnvironment commonEnvironment;
    private final LoggingService<DatasourceErrorLog> datasourceLoggingService;
    private final LoggingService<TimeLimitExceedLog> timeLimitExceedLogLoggingService;

    public CommonServiceLoggingAspect(@Qualifier("datasourceErrorLogService") LoggingService<DatasourceErrorLog> datasourceLoggingService, @Qualifier("timeLimitExceedLogService") LoggingService<TimeLimitExceedLog> timeLimitExceedLogService, CommonEnvironment commonEnvironment) {
        this.commonEnvironment = commonEnvironment;
        this.datasourceLoggingService = datasourceLoggingService;
        this.timeLimitExceedLogLoggingService = timeLimitExceedLogService;
    }

    /**
     * Advice, отлавливающий исключения из методов, помеченных аннотацией {@link LogDatasourceError}
     *
     * @param e Выброшенное методом исключение
     */
    @AfterThrowing(pointcut = "@annotation(com.example.transaction_service.service.common.aop.annotation.LogDatasourceError)", throwing = "e")
    public void logAfterMethodThrowing(Exception e) {
        datasourceLoggingService.log(new DatasourceErrorLog(e.getClass().getSimpleName(), Arrays.toString(e.getStackTrace()), e.getMessage(), Timestamp.valueOf(LocalDateTime.now())));
    }

    /**
     * Advice, отслеживающий время выполнения методов, помеченных с помощью аннотации {@link Metric}
     * и сохраняющий сообщение об превышении времени выполнения, если оно было
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
            if(end - start > commonEnvironment.getMETHOD_RUNTIME_LIMIT()){
                timeLimitExceedLogLoggingService.log(new TimeLimitExceedLog(joinPoint.getSignature().getDeclaringTypeName(), joinPoint.getSignature().getName(), end - start, Timestamp.valueOf(LocalDateTime.now())));
            }
        }
    }
}
