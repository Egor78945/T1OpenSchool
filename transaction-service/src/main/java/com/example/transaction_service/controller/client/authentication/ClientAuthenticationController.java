package com.example.transaction_service.controller.client.authentication;

import com.example.transaction_service.controller.common.advice.handler.CommonControllerExceptionHandler;
import com.example.transaction_service.model.client.dto.ClientDTO;
import com.example.transaction_service.model.client.entity.Client;
import com.example.transaction_service.model.log.entity.DatasourceErrorLog;
import com.example.transaction_service.service.client.authentication.AbstractClientAuthenticationService;
import com.example.transaction_service.service.client.mapper.ClientMapper;
import com.example.transaction_service.service.kafka.producer.KafkaProducerService;
import jakarta.validation.Valid;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.header.internals.RecordHeader;
import org.apache.kafka.common.header.internals.RecordHeaders;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Контроллер, принимающий запросы, связанные с аутентификацией клиентов
 */
@RestController
@RequestMapping("/api/v1/authentication")
@CommonControllerExceptionHandler
public class ClientAuthenticationController {
    private final AbstractClientAuthenticationService<Client, Client> clientAuthenticationService;

    public ClientAuthenticationController(@Qualifier("clientAuthenticationServiceManager") AbstractClientAuthenticationService<Client, Client> clientAuthenticationService) {
        this.clientAuthenticationService = clientAuthenticationService;
    }

    /**
     * Метод, выполняющий регистрацию нового клиента
     * @param clientDTO Регистрационные данные нового клиента {@link Client}
     */
    @PostMapping("/registration")
    public void registration(@RequestBody @Valid ClientDTO clientDTO) {
        //ProducerRecord<String, DatasourceErrorLog> producerRecord = new ProducerRecord<>("t1_demo_metrics", null, "1", new DatasourceErrorLog(), new RecordHeaders(List.of(new RecordHeader("header1", "header1".getBytes()))));
        //kafkaProducerService.send(producerRecord);
        clientAuthenticationService.registration(ClientMapper.mapTo(clientDTO));
    }
}
