package com.example.transaction_service.controller.account;

import com.example.transaction_service.controller.common.advice.handler.CommonControllerExceptionHandler;
import com.example.transaction_service.exception.NotFoundException;
import com.example.transaction_service.model.account.entity.Account;
import com.example.transaction_service.model.account.status.entity.AccountStatus;
import com.example.transaction_service.model.account.status.entity.enumeration.AccountStatusEnumeration;
import com.example.transaction_service.model.account.type.entity.AccountType;
import com.example.transaction_service.model.account.type.enumeration.AccountTypeEnumeration;
import com.example.transaction_service.service.account.AbstractAccountService;
import com.example.transaction_service.service.account.router.AccountServiceRouter;
import com.example.transaction_service.service.account.status.AccountStatusService;
import com.example.transaction_service.service.account.type.AccountTypeService;
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
    private final AccountTypeService<AccountType> accountTypeService;
    private final AccountStatusService<AccountStatus> accountStatusService;

    public AccountController(@Qualifier("accountServiceRouterManager") AccountServiceRouter<AbstractAccountService<Account>> accountServiceRouter, @Qualifier("accountTypeServiceManager") AccountTypeService<AccountType> accountTypeService, @Qualifier("accountStatusServiceManager") AccountStatusService<AccountStatus> accountStatusService) {
        this.accountServiceRouter = accountServiceRouter;
        this.accountTypeService = accountTypeService;
        this.accountStatusService = accountStatusService;
    }

    /**
     * Метод, регистрирующий новый клиентский аккаунт
     * @param clientId Id существующего клиента {@link Client}
     * @param accountTypeId Id существующего типа клиентского аккаунта {@link AccountTypeEnumeration}
     */
    @PostMapping("/registration")
    public ResponseEntity<String> registerAccount(@RequestParam(value = "clientId", defaultValue = "-1") String clientId, @RequestParam(value = "accountTypeId", defaultValue = "1") long accountTypeId) {
        AccountTypeEnumeration accountTypeEnumeration = AccountTypeEnumeration.getById(accountTypeId);
        return ResponseEntity.ok(accountServiceRouter.getByAccountTypeEnumeration(accountTypeEnumeration)
                .save(UUID.fromString(clientId), accountTypeService.getById(accountTypeId), accountStatusService.getById(AccountStatusEnumeration.OPEN.getId())).toString());
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
            accountService = accountServiceRouter.getByAccountTypeEnumeration(AccountTypeEnumeration.getById(accountTypeId));
            return ResponseEntity.ok(accountService.getByClientIdAndAccountType(UUID.fromString(clientId)));
        } else {
            accountService = accountServiceRouter.getByAccountTypeEnumeration(AccountTypeEnumeration.DEBIT);
            return ResponseEntity.ok(accountService.getByClientId(UUID.fromString(clientId)));
        }

    }

}
