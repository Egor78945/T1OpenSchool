package com.example.transaction_service.controller.account;

import com.example.transaction_service.controller.common.advice.handler.CommonControllerExceptionHandler;
import com.example.transaction_service.model.account.entity.Account;
import com.example.transaction_service.model.account.status.entity.AccountStatus;
import com.example.transaction_service.model.account.status.entity.enumeration.AccountStatusEnumeration;
import com.example.transaction_service.model.account.type.entity.AccountType;
import com.example.transaction_service.model.account.type.enumeration.AccountTypeEnumeration;
import com.example.transaction_service.model.user.entity.User;
import com.example.transaction_service.service.account.AbstractAccountService;
import com.example.transaction_service.service.account.router.AccountServiceRouter;
import com.example.transaction_service.service.account.status.AccountStatusService;
import com.example.transaction_service.service.account.type.AccountTypeService;
import com.example.transaction_service.service.client.AbstractClientService;
import com.example.transaction_service.service.common.authentication.AuthenticationContextService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.transaction_service.model.client.entity.Client;

import java.util.List;

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
    private final AbstractClientService<Client> clientService;
    private final AuthenticationContextService<User> userAuthenticationContextService;

    public AccountController(@Qualifier("accountServiceRouterManager") AccountServiceRouter<AbstractAccountService<Account>> accountServiceRouter, @Qualifier("accountTypeServiceManager") AccountTypeService<AccountType> accountTypeService, @Qualifier("accountStatusServiceManager") AccountStatusService<AccountStatus> accountStatusService, @Qualifier("clientServiceManager") AbstractClientService<Client> clientService, @Qualifier("userAuthenticationContextServiceManager") AuthenticationContextService<User> userAuthenticationContextService) {
        this.accountServiceRouter = accountServiceRouter;
        this.accountTypeService = accountTypeService;
        this.accountStatusService = accountStatusService;
        this.clientService = clientService;
        this.userAuthenticationContextService = userAuthenticationContextService;
    }

    /**
     * Метод, регистрирующий новый клиентский аккаунт
     * @param accountTypeId Id существующего типа клиентского аккаунта {@link AccountTypeEnumeration}
     */
    @PostMapping("/registration")
    public ResponseEntity<String> registerAccount(@RequestParam(value = "accountTypeId", defaultValue = "1") long accountTypeId) {
        AccountTypeEnumeration accountTypeEnumeration = AccountTypeEnumeration.getById(accountTypeId);
        return ResponseEntity.ok(accountServiceRouter.getByAccountTypeEnumeration(accountTypeEnumeration)
                .save(clientService.getByUserId(userAuthenticationContextService.getCurrentAuthentication().getId()).getClient_id(), accountTypeService.getById(accountTypeId), accountStatusService.getById(AccountStatusEnumeration.OPEN.getId())).toString());
    }

    /**
     * Метод, возвращающий список аккаунтов определённого клиента по его Id
     * @param accountTypeId Id существующего типа клиентского аккаунта {@link AccountTypeEnumeration}
     * @return Список клиентских аккаунтов {@link List}
     */
    @GetMapping
    public ResponseEntity<List<Account>> getAccountsByClientId(@RequestParam(value = "accountTypeId", defaultValue = "-1") long accountTypeId) {
        AbstractAccountService<Account> accountService;
        Client client = clientService.getByUserId(userAuthenticationContextService.getCurrentAuthentication().getId());
        if (accountTypeId != -1) {
            accountService = accountServiceRouter.getByAccountTypeEnumeration(AccountTypeEnumeration.getById(accountTypeId));
            return ResponseEntity.ok(accountService.getByClientIdAndAccountType(client.getClient_id()));
        } else {
            accountService = accountServiceRouter.getByAccountTypeEnumeration(AccountTypeEnumeration.DEBIT);
            return ResponseEntity.ok(accountService.getByClientId(client.getClient_id()));
        }
    }

}
