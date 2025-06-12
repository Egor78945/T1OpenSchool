package com.example.aop_starter.service.common.logging.implementation;

import com.example.aop_starter.model.log.entity.TimeLimitExceedLog;
import com.example.aop_starter.repository.TimeLimitExceedLogRepository;
import com.example.aop_starter.service.common.logging.LoggingService;
import org.springframework.stereotype.Service;

@Service
public class TimeLimitExceedLogService implements LoggingService<TimeLimitExceedLog> {
    private final TimeLimitExceedLogRepository timeLimitExceedLogRepository;

    public TimeLimitExceedLogService(TimeLimitExceedLogRepository timeLimitExceedLogRepository) {
        this.timeLimitExceedLogRepository = timeLimitExceedLogRepository;
    }

    @Override
    public void log(TimeLimitExceedLog loggingSubject) {
        timeLimitExceedLogRepository.save(loggingSubject);
    }
}
