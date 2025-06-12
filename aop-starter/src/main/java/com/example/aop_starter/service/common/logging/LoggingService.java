package com.example.aop_starter.service.common.logging;

public interface LoggingService<L> {
    void log(L loggingSubject);
}
