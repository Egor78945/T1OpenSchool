package com.example.aop_starter.service.common.logging.implementation;

import com.example.aop_starter.model.log.entity.DatasourceErrorLog;
import com.example.aop_starter.repository.DatasourceErrorLogRepository;
import com.example.aop_starter.service.common.logging.LoggingService;
import org.springframework.stereotype.Service;

@Service
public class DatasourceErrorLogService implements LoggingService<DatasourceErrorLog> {
    private final DatasourceErrorLogRepository datasourceErrorLogRepository;

    public DatasourceErrorLogService(DatasourceErrorLogRepository datasourceErrorLogRepository) {
        this.datasourceErrorLogRepository = datasourceErrorLogRepository;
    }

    @Override
    public void log(DatasourceErrorLog loggingSubject) {
        datasourceErrorLogRepository.save(loggingSubject);
    }
}
