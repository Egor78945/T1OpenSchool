package com.example.transaction_service.service.client.monitor;

import com.example.transaction_service.service.common.monitoring.MonitoringService;

public interface BlockedClientMonitoringService extends MonitoringService {
    @Override
    void monitor();
}
