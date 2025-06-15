package com.example.blocking_service.service.account.implementation;

import com.example.blocking_service.exception.NotFoundException;
import com.example.blocking_service.exception.ProcessingException;
import com.example.blocking_service.model.account.entity.Account;
import com.example.blocking_service.repository.AccountRepository;
import com.example.blocking_service.service.account.AccountService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AccountServiceManager implements AccountService<Account> {
    private final AccountRepository accountRepository;

    public AccountServiceManager(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public Account update(Account account) {
        if(accountRepository.existsById(account.getId()) && accountRepository.existsAccountByAccountId(account.getAccountId())){
            return accountRepository.save(account);
        }
        throw new ProcessingException(String.format("account can not be updated\nAccount : %s", account));
    }

    @Override
    public Account getById(long id) {
        return accountRepository.findById(id).orElseThrow(() -> new NotFoundException(String.format("account is not found by id\nid : %s", id)));
    }

    @Override
    public Account getByNaturalId(UUID accountId) {
        return accountRepository.findAccountByAccountId(accountId).orElseThrow(() -> new NotFoundException(String.format("account is not found by account id\naccount id : %s", accountId)));
    }

    @Override
    @Transactional
    public void changeStatusByNaturalIdAndStatusId(UUID naturalId, long statusId) {
        accountRepository.updateAccountStatusByAccount_idAndAccountStatusId(naturalId, statusId);
    }

    @Override
    public boolean existsByNaturalId(UUID accountId) {
        return accountRepository.existsAccountByAccountId(accountId);
    }

    @Override
    public boolean existsById(long id) {
        return accountRepository.existsById(id);
    }
}
