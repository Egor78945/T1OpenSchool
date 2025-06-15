package com.example.blocking_service.controller.client;

import com.example.blocking_service.model.client.dto.ClientListDTO;
import com.example.blocking_service.model.client.entity.Client;
import com.example.blocking_service.service.common.ProcessorService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/client")
public class ClientController {
    private final ProcessorService<Client> clientProcessorService;

    public ClientController(@Qualifier("clientProcessorServiceManager") ProcessorService<Client> clientProcessorService) {
        this.clientProcessorService = clientProcessorService;
    }

    @PutMapping("/unblock")
    public void unblockClient(@RequestBody ClientListDTO clientIdList){
        System.out.println("BEFORE UNBLOCK CLIENT");
        clientProcessorService.unblock(clientIdList.getClients());
        System.out.println("AFTER UNBLOCK CLIENT");
    }
}
