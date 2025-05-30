package com.example.transaction_service.service.logging.router;

import com.example.transaction_service.model.log.Log;
import com.example.transaction_service.service.logging.LoggingService;

import java.util.Optional;

/**
 * Интерфейс, предоставляющий функционал по машрутизации сервисов по логгиррованию {@link LoggingService}
 */
public interface LoggingServiceRouter {
    /**
     * Получить сервис по логгированию {@link LoggingService} по типу логируемого объекта
     * @param targetClass Класс логгируемого объекта
     * @return Сервис по логгированию {@link LoggingService}
     * @param <T> Тип объекта логгирования
     */
    <T extends Log> Optional<LoggingService<T>> getByTargetClass(Class<T> targetClass);
}
