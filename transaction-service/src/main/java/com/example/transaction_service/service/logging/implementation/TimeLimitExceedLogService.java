package com.example.transaction_service.service.logging.implementation;

import com.example.transaction_service.model.log.entity.DatasourceErrorLog;
import com.example.transaction_service.model.log.entity.TimeLimitExceedLog;
import com.example.transaction_service.repository.TimeLimitExceedLogRepository;
import com.example.transaction_service.service.common.aop.annotation.LogDatasourceError;
import com.example.transaction_service.service.common.aop.annotation.Metric;
import com.example.transaction_service.service.logging.LoggingService;
import org.springframework.stereotype.Service;

/**
 * Реализация сервиса по логгированию {@link LoggingService} для логгирования {@link TimeLimitExceedLog}
 */
@Service
public class TimeLimitExceedLogService implements LoggingService<TimeLimitExceedLog> {
    private final TimeLimitExceedLogRepository timeLimitExceedLogRepository;

    public TimeLimitExceedLogService(TimeLimitExceedLogRepository timeLimitExceedLogRepository) {
        this.timeLimitExceedLogRepository = timeLimitExceedLogRepository;
    }

    @Override
    @LogDatasourceError
    @Metric
    public void log(TimeLimitExceedLog loggingSubject) {
        timeLimitExceedLogRepository.save(loggingSubject);
    }
}
