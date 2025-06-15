package com.example.transaction_service.configuration.actuator.client.metric;

import com.example.transaction_service.model.client.status.enumeration.ClientStatusEnumeration;
import com.example.transaction_service.repository.ClientRepository;
import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicLong;

@Component
public class BlockedClientMetric {
    private final ClientRepository clientRepository;
    private final AtomicLong blockedCount;
    public BlockedClientMetric(MeterRegistry meterRegistry, ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
        this.blockedCount = new AtomicLong(0);
        Gauge
                .builder("transaction_service_client_blocked_count", blockedCount, AtomicLong::get)
                .register(meterRegistry);
    }

    @Scheduled(fixedDelay = 60_000)
    public void updateBlockedClientsCount(){
        blockedCount.set(clientRepository.findCountClientsByClientStatusId(ClientStatusEnumeration.BLOCKED.getId()));
    }
}
