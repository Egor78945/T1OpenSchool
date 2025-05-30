package com.example.transaction_service.service.logging.implementation;

import com.example.transaction_service.model.log.entity.DatasourceErrorLog;
import com.example.transaction_service.repository.DatasourceErrorLogRepository;
import com.example.transaction_service.service.common.aop.annotation.LogDatasourceError;
import com.example.transaction_service.service.common.aop.annotation.Metric;
import com.example.transaction_service.service.logging.LoggingService;
import org.springframework.stereotype.Repository;

/**
 * Реализация сервиса по логгированию {@link LoggingService} для логгирования {@link DatasourceErrorLog}
 */
@Repository
public class DatasourceErrorLogService implements LoggingService<DatasourceErrorLog> {
    private final DatasourceErrorLogRepository datasourceErrorLogRepository;

    public DatasourceErrorLogService(DatasourceErrorLogRepository datasourceErrorLogRepository) {
        this.datasourceErrorLogRepository = datasourceErrorLogRepository;
    }

    @Override
    @LogDatasourceError
    @Metric
    public void log(DatasourceErrorLog loggingSubject) {
        datasourceErrorLogRepository.save(loggingSubject);
    }
}
