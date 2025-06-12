package com.example.aop_starter.service.common.logging.implementation;

import com.example.aop_starter.model.log.entity.DatasourceErrorLog;
import com.example.aop_starter.repository.DatasourceErrorLogRepository;
import com.example.aop_starter.service.common.logging.StarterLoggingService;
import org.springframework.stereotype.Service;

@Service
public class DatasourceErrorLogServiceStarter implements StarterLoggingService<DatasourceErrorLog> {
    private final DatasourceErrorLogRepository datasourceErrorLogRepository;

    public DatasourceErrorLogServiceStarter(DatasourceErrorLogRepository datasourceErrorLogRepository) {
        this.datasourceErrorLogRepository = datasourceErrorLogRepository;
    }

    @Override
    public void log(DatasourceErrorLog loggingSubject) {
        datasourceErrorLogRepository.save(loggingSubject);
    }
}
