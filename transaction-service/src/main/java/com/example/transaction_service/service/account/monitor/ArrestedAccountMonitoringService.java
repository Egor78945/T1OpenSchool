package com.example.transaction_service.service.account.monitor;

import com.example.transaction_service.service.common.monitoring.MonitoringService;

public interface ArrestedAccountMonitoringService extends MonitoringService {
    @Override
    void monitor();
}
