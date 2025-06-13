package com.example.transaction_service.service.account.type.implementation;

import com.example.aop_starter.service.common.aop.annotation.Cached;
import com.example.transaction_service.exception.NotFoundException;
import com.example.transaction_service.model.account.type.entity.AccountType;
import com.example.transaction_service.repository.AccountTypeRepository;
import com.example.transaction_service.service.account.type.AccountTypeService;
import org.springframework.stereotype.Service;

@Service
public class AccountTypeServiceManager implements AccountTypeService<AccountType> {
    private final AccountTypeRepository accountTypeRepository;

    public AccountTypeServiceManager(AccountTypeRepository accountTypeRepository) {
        this.accountTypeRepository = accountTypeRepository;
    }

    @Override
    @Cached
    public AccountType getById(long id) {
        return accountTypeRepository.findById(id).orElseThrow(() -> new NotFoundException(String.format("unknown account type by id\naccount type id : %s", id)));
    }
}
