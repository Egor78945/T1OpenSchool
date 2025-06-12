package com.example.aop_starter.service.common.logging.router.implementation;

import com.example.aop_starter.model.log.Log;
import com.example.aop_starter.model.log.entity.DatasourceErrorLog;
import com.example.aop_starter.model.log.entity.TimeLimitExceedLog;
import com.example.aop_starter.service.common.logging.LoggingService;
import com.example.aop_starter.service.common.logging.implementation.DatasourceErrorLogService;
import com.example.aop_starter.service.common.logging.implementation.TimeLimitExceedLogService;
import com.example.aop_starter.service.common.logging.router.LoggingServiceRouter;

import java.util.Map;
import java.util.Optional;

public class LoggingServiceRouterManager implements LoggingServiceRouter {
    private final Map<Class<? extends Log>, LoggingService<? extends Log>> loggingServiceMap;

    public LoggingServiceRouterManager(DatasourceErrorLogService datasourceErrorLogService, TimeLimitExceedLogService timeLimitExceedLogService) {
        this.loggingServiceMap = Map.of(DatasourceErrorLog.class, datasourceErrorLogService, TimeLimitExceedLog.class, timeLimitExceedLogService);
    }

    @Override
    public <T extends Log> Optional<LoggingService<T>> getByTargetClass(Class<T> targetClass) {
        return Optional.ofNullable((LoggingService<T>) loggingServiceMap.get(targetClass));
    }
}
