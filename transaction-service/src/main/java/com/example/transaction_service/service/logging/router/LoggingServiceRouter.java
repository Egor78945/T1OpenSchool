package com.example.transaction_service.service.logging.router;

import com.example.transaction_service.model.log.Log;
import com.example.transaction_service.service.logging.LoggingService;

import java.util.Optional;

public interface LoggingServiceRouter {
    <T extends Log> Optional<LoggingService<T>> getByTargetClass(Class<T> targetClass);
}
