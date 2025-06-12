package com.example.aop_starter.service.common.logging.router.implementation;

import com.example.aop_starter.model.log.Log;
import com.example.aop_starter.model.log.entity.DatasourceErrorLog;
import com.example.aop_starter.model.log.entity.TimeLimitExceedLog;
import com.example.aop_starter.service.common.logging.StarterLoggingService;
import com.example.aop_starter.service.common.logging.implementation.DatasourceErrorLogServiceStarter;
import com.example.aop_starter.service.common.logging.implementation.TimeLimitExceedLogServiceStarter;
import com.example.aop_starter.service.common.logging.router.StarterLoggingServiceRouter;

import java.util.Map;
import java.util.Optional;

public class StarterLoggingServiceRouterManager implements StarterLoggingServiceRouter {
    private final Map<Class<? extends Log>, StarterLoggingService<? extends Log>> loggingServiceMap;

    public StarterLoggingServiceRouterManager(DatasourceErrorLogServiceStarter datasourceErrorLogService, TimeLimitExceedLogServiceStarter timeLimitExceedLogService) {
        this.loggingServiceMap = Map.of(DatasourceErrorLog.class, datasourceErrorLogService, TimeLimitExceedLog.class, timeLimitExceedLogService);
    }

    @Override
    public <T extends Log> Optional<StarterLoggingService<T>> getByTargetClass(Class<T> targetClass) {
        return Optional.ofNullable((StarterLoggingService<T>) loggingServiceMap.get(targetClass));
    }
}
