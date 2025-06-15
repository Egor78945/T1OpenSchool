package com.example.transaction_service.configuration.actuator.account.metric;

import com.example.transaction_service.model.account.status.entity.enumeration.AccountStatusEnumeration;
import com.example.transaction_service.repository.AccountRepository;
import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicLong;

@Component
public class ArrestedAccountMetric {
    private final AccountRepository accountRepository;
    private final AtomicLong arrestedCount;

    public ArrestedAccountMetric(MeterRegistry meterRegistry, AccountRepository accountRepository) {
        arrestedCount = new AtomicLong(0);
        this.accountRepository = accountRepository;
        Gauge
                .builder("transaction_service_account_arrested_count", arrestedCount, AtomicLong::get)
                .register(meterRegistry);
    }

    @Scheduled(fixedDelay = 60_000)
    public void updateArrestedAccountsCount(){
        arrestedCount.set(accountRepository.findAccountCountByAccountStatusId(AccountStatusEnumeration.ARRESTED.getId()));
    }
}
