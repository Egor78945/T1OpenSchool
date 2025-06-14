package com.example.blocking_service.controller.client;

import com.example.blocking_service.model.client.dto.ClientIdListDTO;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/client")
public class ClientController {
    @PutMapping("/unblock")
    public void unblockClient(@RequestBody ClientIdListDTO clientIdList){

    }
}
