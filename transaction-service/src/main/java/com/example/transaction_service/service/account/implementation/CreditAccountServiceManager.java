package com.example.transaction_service.service.account.implementation;

import com.example.transaction_service.environment.account.AccountEnvironment;
import com.example.transaction_service.exception.InvalidDataException;
import com.example.transaction_service.exception.NotFoundException;
import com.example.transaction_service.model.account.entity.Account;
import com.example.transaction_service.model.account.status.entity.AccountStatus;
import com.example.transaction_service.model.account.type.entity.AccountType;
import com.example.transaction_service.model.account.type.enumeration.AccountTypeEnumeration;
import com.example.transaction_service.model.client.entity.Client;
import com.example.transaction_service.repository.AccountRepository;
import com.example.transaction_service.repository.AccountStatusRepository;
import com.example.transaction_service.repository.AccountTypeRepository;
import com.example.transaction_service.repository.ClientRepository;
import com.example.transaction_service.service.account.AbstractCreditAccountService;
import com.example.transaction_service.service.client.AbstractClientService;
import com.example.transaction_service.service.common.aop.annotation.Cached;
import com.example.transaction_service.service.common.aop.annotation.LogDatasourceError;
import com.example.transaction_service.service.common.aop.annotation.Metric;
import org.hibernate.annotations.Cache;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * Класс, выполняющий действия, связанные с клиентскими аккаунтами {@link Account} типа <b>CREDIT</b>
 */
@Service
public class CreditAccountServiceManager extends AbstractCreditAccountService<Account> {
    private final AccountEnvironment accountEnvironment;
    private final AbstractClientService<Client> clientService;

    public CreditAccountServiceManager(AccountRepository accountRepository, AccountTypeRepository accountTypeRepository, AccountStatusRepository accountStatusRepository, @Qualifier("clientServiceManager") AbstractClientService<Client> clientService, AccountEnvironment accountEnvironment) {
        super(accountRepository, accountTypeRepository, accountStatusRepository);
        this.accountEnvironment = accountEnvironment;
        this.clientService = clientService;
    }

    @Override
    @LogDatasourceError
    @Metric
    public UUID save(UUID clientId, AccountType accountType, AccountStatus accountStatus) {
        Client client = clientService.getByClientId(clientId);
        if (accountType.getId() == AccountTypeEnumeration.CREDIT.getId() && accountRepository.findAccountCountByClientIdAndAccountTypeId(clientId, AccountTypeEnumeration.CREDIT.getId()) < 1) {
            Account account = new Account(client, buildUUID(), accountType, accountStatus);
            account.setBalance(accountEnvironment.getACCOUNT_CREDIT_START_BALANCE());
            return accountRepository.save(account).getAccountId();
        } else {
            throw new InvalidDataException(String.format("account can not be saved as credit one\naccount type : %s", accountType));
        }
    }

    @Override
    public Account update(Account account) {
        if(accountRepository.existsById(account.getId()) && accountRepository.existsAccountByAccountId(account.getAccountId())){
            return accountRepository.save(account);
        } else {
            return account;
        }
    }

    @Override
    public List<Account> getByClientIdAndAccountType(UUID clientId) {
        return accountRepository.findAccountByClientIdAndAccountTypeId(clientId, AccountTypeEnumeration.CREDIT.getId());
    }
}
