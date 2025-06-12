package com.example.aop_starter.service.common.logging.router;

import com.example.aop_starter.model.log.Log;
import com.example.aop_starter.service.common.logging.StarterLoggingService;

import java.util.Optional;

public interface StarterLoggingServiceRouter {
    <T extends Log> Optional<StarterLoggingService<T>> getByTargetClass(Class<T> targetClass);
}
