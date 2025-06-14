package com.example.blocking_service.controller.account;

import com.example.blocking_service.model.account.dto.AccountListDTO;
import com.example.blocking_service.model.account.entity.Account;
import com.example.blocking_service.service.common.ProcessorService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/account")
public class AccountController {
    private final ProcessorService<Account> accountProcessorService;

    public AccountController(@Qualifier("accountProcessorServiceManager") ProcessorService<Account> accountProcessorService) {
        this.accountProcessorService = accountProcessorService;
    }

    @PutMapping("/unarrest")
    public void unblockClient(@RequestBody AccountListDTO accountListDTO) {
        accountProcessorService.unblock(accountListDTO.getAccounts());
    }
}
