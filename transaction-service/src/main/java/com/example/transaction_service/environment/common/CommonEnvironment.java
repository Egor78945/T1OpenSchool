package com.example.transaction_service.environment.common;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class CommonEnvironment {
    private final long METHOD_RUNTIME_LIMIT;

    public CommonEnvironment(@Value("${method.runtime.limit}") long METHOD_RUNTIME_LIMIT) {
        this.METHOD_RUNTIME_LIMIT = METHOD_RUNTIME_LIMIT;
    }

    public long getMETHOD_RUNTIME_LIMIT() {
        return METHOD_RUNTIME_LIMIT;
    }
}
