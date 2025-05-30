package com.example.transaction_service.service.logging.router.implementation;

import com.example.transaction_service.model.log.Log;
import com.example.transaction_service.model.log.entity.DatasourceErrorLog;
import com.example.transaction_service.model.log.entity.TimeLimitExceedLog;
import com.example.transaction_service.service.logging.LoggingService;
import com.example.transaction_service.service.logging.implementation.DatasourceErrorLogService;
import com.example.transaction_service.service.logging.implementation.TimeLimitExceedLogService;
import com.example.transaction_service.service.logging.router.LoggingServiceRouter;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
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
