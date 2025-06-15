package com.example.transaction_service.service.client.monitor.implementation;

import com.example.transaction_service.environment.client.ClientEnvironment;
import com.example.transaction_service.environment.web.WebClientEnvironment;
import com.example.transaction_service.model.client.dto.ClientListDTO;
import com.example.transaction_service.model.client.entity.Client;
import com.example.transaction_service.model.client.status.enumeration.ClientStatusEnumeration;
import com.example.transaction_service.service.client.AbstractClientService;
import com.example.transaction_service.service.client.monitor.BlockedClientMonitoringService;
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
public class BlockedClientMonitoringServiceManager implements BlockedClientMonitoringService {
    private final AbstractClientService<Client> clientService;
    private final WebClientService<ClassicHttpRequest, String> webClientService;
    private final ClientEnvironment clientEnvironment;
    private final WebClientEnvironment webClientEnvironment;
    private final ObjectMapper objectMapper;

    public BlockedClientMonitoringServiceManager(@Qualifier("clientServiceManager") AbstractClientService<Client> clientService, @Qualifier("webClientServiceManager") WebClientService<ClassicHttpRequest, String> webClientService, ClientEnvironment clientEnvironment, WebClientEnvironment webClientEnvironment, ObjectMapper objectMapper) {
        this.clientService = clientService;
        this.webClientService = webClientService;
        this.clientEnvironment = clientEnvironment;
        this.webClientEnvironment = webClientEnvironment;
        this.objectMapper = objectMapper;
    }

    @Override
    @Scheduled(initialDelay = 180_000, fixedDelay = 60_000)
    public void monitor() {
        List<Client> blockedClients = clientService.getByClientStatusIdAndCount(ClientStatusEnumeration.BLOCKED.getId(), clientEnvironment.getCLIENT_BLOCKED_MAX_SELECT_COUNT());
        if(!blockedClients.isEmpty()) {
            try {
                webClientService.sendRequest(WebClientBuilder.build(String.format("http://%s:%s/api/v1/client/unblock", webClientEnvironment.getBLOCKING_SERVICE_HOST(), webClientEnvironment.getBLOCKING_SERVICE_PORT_INNER()), new StringEntity(objectMapper.writeValueAsString(new ClientListDTO(blockedClients)), ContentType.APPLICATION_JSON)));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
