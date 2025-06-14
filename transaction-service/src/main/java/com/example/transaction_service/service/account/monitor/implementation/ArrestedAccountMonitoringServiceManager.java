package com.example.transaction_service.service.account.monitor.implementation;

import com.example.transaction_service.environment.account.AccountEnvironment;
import com.example.transaction_service.environment.web.WebClientEnvironment;
import com.example.transaction_service.model.account.dto.AccountListDTO;
import com.example.transaction_service.model.account.entity.Account;
import com.example.transaction_service.model.account.status.entity.enumeration.AccountStatusEnumeration;
import com.example.transaction_service.service.account.AbstractAccountService;
import com.example.transaction_service.service.account.monitor.ArrestedAccountMonitoringService;
import com.example.transaction_service.service.common.client.WebClientService;
import com.example.transaction_service.service.common.client.builder.WebClientBuilder;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.hc.core5.http.ClassicHttpRequest;
import org.apache.hc.core5.http.ContentType;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class ArrestedAccountMonitoringServiceManager implements ArrestedAccountMonitoringService {
    private final WebClientService<ClassicHttpRequest, String> clientService;
    private final AbstractAccountService<Account> accountService;
    private final AccountEnvironment accountEnvironment;
    private final WebClientEnvironment webClientEnvironment;
    private final ObjectMapper objectMapper;

    public ArrestedAccountMonitoringServiceManager(@Qualifier("webClientServiceManager") WebClientService<ClassicHttpRequest, String> clientService, @Qualifier("debitAccountServiceManager") AbstractAccountService<Account> accountService, AccountEnvironment accountEnvironment, WebClientEnvironment webClientEnvironment, ObjectMapper objectMapper) {
        this.clientService = clientService;
        this.accountService = accountService;
        this.accountEnvironment = accountEnvironment;
        this.webClientEnvironment = webClientEnvironment;
        this.objectMapper = objectMapper;
    }

    @Override
    @Scheduled(initialDelay = 180_000, fixedDelay = 60_000)
    public void monitor() {
        List<Account> arrestedAccounts = accountService.getByAccountStatusIdAndCount(AccountStatusEnumeration.ARRESTED.getId(), accountEnvironment.getACCOUNT_ARRESTED_MAX_SELECT_COUNT());
        if(!arrestedAccounts.isEmpty()) {
            try {
                clientService.sendRequest(WebClientBuilder.build(String.format("http://%s:%s/api/v1/account/unarrest", webClientEnvironment.getBLOCKING_SERVICE_HOST(), webClientEnvironment.getBLOCKING_SERVICE_PORT_INNER()), new StringEntity(objectMapper.writeValueAsString(new AccountListDTO(arrestedAccounts)), ContentType.APPLICATION_JSON)));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
