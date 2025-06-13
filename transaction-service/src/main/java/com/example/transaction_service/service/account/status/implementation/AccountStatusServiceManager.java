package com.example.transaction_service.service.account.status.implementation;

import com.example.aop_starter.service.common.aop.annotation.Cached;
import com.example.transaction_service.exception.NotFoundException;
import com.example.transaction_service.model.account.status.entity.AccountStatus;
import com.example.transaction_service.repository.AccountStatusRepository;
import com.example.transaction_service.service.account.status.AccountStatusService;
import org.springframework.stereotype.Service;

/**
 * Класс, реализующий интерфейс {@link AccountStatusService}
 */
@Service
public class AccountStatusServiceManager implements AccountStatusService<AccountStatus> {
    private final AccountStatusRepository accountStatusRepository;

    public AccountStatusServiceManager(AccountStatusRepository accountStatusRepository) {
        this.accountStatusRepository = accountStatusRepository;
    }

    @Override
    @Cached
    public AccountStatus getById(long id) {
        return accountStatusRepository.findById(id).orElseThrow(() -> new NotFoundException(String.format("unknown account status by id\naccount status id : %s", id)));
    }
}
