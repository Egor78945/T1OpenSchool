package com.example.transaction_service.controller.account;

import com.example.transaction_service.controller.common.advice.handler.CommonControllerExceptionHandler;
import com.example.transaction_service.exception.NotFoundException;
import com.example.transaction_service.model.account.entity.Account;
import com.example.transaction_service.model.account.type.enumeration.AccountTypeEnumeration;
import com.example.transaction_service.service.account.AbstractAccountService;
import com.example.transaction_service.service.account.router.AccountServiceRouter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.transaction_service.model.client.entity.Client;

import java.util.List;
import java.util.UUID;

/**
 * Контроллер, принимающий запросы, связанные с клиентскими аккаунтами
 */
@RestController
@RequestMapping("/api/v1/account")
@CommonControllerExceptionHandler
public class AccountController {
    private final AccountServiceRouter<AbstractAccountService<Account>> accountServiceRouter;

    public AccountController(@Qualifier("accountServiceRouterManager") AccountServiceRouter<AbstractAccountService<Account>> accountServiceRouter) {
        this.accountServiceRouter = accountServiceRouter;
    }

    /**
     * Метод, регистрирующий новый клиентский аккаунт
     * @param clientId Id существующего клиента {@link Client}
     * @param accountTypeId Id существующего типа клиентского аккаунта {@link AccountTypeEnumeration}
     */
    @PostMapping("/registration")
    public void registerAccount(@RequestParam(value = "clientId", defaultValue = "-1") String clientId, @RequestParam(value = "accountTypeId", defaultValue = "1") long accountTypeId) {
        AccountTypeEnumeration accountTypeEnumeration = AccountTypeEnumeration.getById(accountTypeId)
                .orElseThrow(() -> new NotFoundException(String.format("unknown account type\naccount type id : %s", accountTypeId)));
        accountServiceRouter.getByAccountTypeEnumeration(accountTypeEnumeration)
                .orElseThrow(() -> new NotFoundException(String.format("account service by AccountTypeEnumeration is not found.\nAccountTypeEnumeration : %s", accountTypeEnumeration)))
                .save(UUID.fromString(clientId), accountTypeId);
    }

    /**
     * Метод, возвращающий список аккаунтов определённого клиента по его Id
     * @param clientId Id существующего клиента {@link Client}
     * @param accountTypeId Id существующего типа клиентского аккаунта {@link AccountTypeEnumeration}
     * @return Список клиентских аккаунтов {@link List}
     */
    @GetMapping("/{clientId}")
    public ResponseEntity<List<Account>> getAccountsByClientId(@PathVariable("clientId") String clientId, @RequestParam(value = "accountTypeId", defaultValue = "-1") long accountTypeId) {
        AbstractAccountService<Account> accountService;
        if (accountTypeId != -1) {
            AccountTypeEnumeration accountTypeEnumeration = AccountTypeEnumeration.getById(accountTypeId).orElseThrow(() ->
                    new NotFoundException(String.format("unknown account type id\nid : %s", accountTypeId)));
            accountService = accountServiceRouter.getByAccountTypeEnumeration(accountTypeEnumeration).orElseThrow(() -> new NotFoundException(String.format("account service by AccountTypeEnumeration is not found.\nAccountTypeEnumeration : %s", accountTypeEnumeration)));
            return ResponseEntity.ok(accountService.getByClientIdAndAccountType(UUID.fromString(clientId)));
        } else {
            accountService = accountServiceRouter.getByAccountTypeEnumeration(AccountTypeEnumeration.DEBIT).get();
            return ResponseEntity.ok(accountService.getByClientId(UUID.fromString(clientId)));
        }

    }

}
