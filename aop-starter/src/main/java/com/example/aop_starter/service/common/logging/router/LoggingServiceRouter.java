package com.example.aop_starter.service.common.logging.router;

import com.example.aop_starter.model.log.Log;
import com.example.aop_starter.service.common.logging.LoggingService;

import java.util.Optional;

public interface LoggingServiceRouter {
    <T extends Log> Optional<LoggingService<T>> getByTargetClass(Class<T> targetClass);
}
