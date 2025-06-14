package com.example.blocking_service.service.account.processor;

import com.example.blocking_service.model.account.entity.Account;
import com.example.blocking_service.model.account.status.entity.enumeration.AccountStatusEnumeration;
import com.example.blocking_service.service.account.AccountService;
import com.example.blocking_service.service.common.ProcessorService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountProcessorServiceManager implements ProcessorService<Account> {
    private final AccountService<Account> accountService;

    public AccountProcessorServiceManager(@Qualifier("accountServiceManager") AccountService<Account> accountService) {
        this.accountService = accountService;
    }

    @Override
    public void unblock(Account account) {
        if (accountService.existsById(account.getId())) {
            accountService.changeStatusByNaturalIdAndStatusId(account.getAccountId(), AccountStatusEnumeration.OPEN.getId());
        }
    }

    @Override
    public void unblock(List<Account> accounts) {
        for (Account a : accounts) {
            unblock(a);
        }
    }
}
