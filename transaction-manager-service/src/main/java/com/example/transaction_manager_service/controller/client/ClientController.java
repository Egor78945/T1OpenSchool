package com.example.transaction_manager_service.controller.client;

import com.example.transaction_manager_service.model.client.dto.ClientAccountDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Random;

@RestController
@RequestMapping("/api/v1/client")
public class ClientController {
    private final Random random;

    public ClientController() {
        this.random = new Random();
    }

    @GetMapping("/blocked")
    public ResponseEntity<Boolean> checkClientIsInBlackList(@RequestBody ClientAccountDTO clientAccountDTO){
        return ResponseEntity.ok(random.nextBoolean());
    }
}
