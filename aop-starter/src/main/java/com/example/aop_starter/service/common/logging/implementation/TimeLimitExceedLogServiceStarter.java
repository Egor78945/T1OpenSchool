package com.example.aop_starter.service.common.logging.implementation;

import com.example.aop_starter.model.log.entity.TimeLimitExceedLog;
import com.example.aop_starter.repository.TimeLimitExceedLogRepository;
import com.example.aop_starter.service.common.logging.StarterLoggingService;
import org.springframework.stereotype.Service;

@Service
public class TimeLimitExceedLogServiceStarter implements StarterLoggingService<TimeLimitExceedLog> {
    private final TimeLimitExceedLogRepository timeLimitExceedLogRepository;

    public TimeLimitExceedLogServiceStarter(TimeLimitExceedLogRepository timeLimitExceedLogRepository) {
        this.timeLimitExceedLogRepository = timeLimitExceedLogRepository;
    }

    @Override
    public void log(TimeLimitExceedLog loggingSubject) {
        timeLimitExceedLogRepository.save(loggingSubject);
    }
}
