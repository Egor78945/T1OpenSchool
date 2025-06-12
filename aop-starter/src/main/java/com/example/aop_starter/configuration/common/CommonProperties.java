package com.example.aop_starter.configuration.common;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "method")
public class CommonProperties {
    private int runtimeLimit;

    public CommonProperties(int runtimeLimit) {
        this.runtimeLimit = runtimeLimit;
    }

    public CommonProperties() {
    }

    public int getRuntimeLimit() {
        return runtimeLimit;
    }

    public void setRuntimeLimit(int runtimeLimit) {
        this.runtimeLimit = runtimeLimit;
    }
}
