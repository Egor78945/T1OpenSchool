package com.example.transaction_service.service.account.implementation;

import com.example.transaction_service.environment.account.AccountEnvironment;
import com.example.transaction_service.exception.InvalidDataException;
import com.example.transaction_service.exception.NotFoundException;
import com.example.transaction_service.model.account.entity.Account;
import com.example.transaction_service.model.account.type.entity.AccountType;
import com.example.transaction_service.model.account.type.enumeration.AccountTypeEnumeration;
import com.example.transaction_service.model.client.entity.Client;
import com.example.transaction_service.repository.AccountRepository;
import com.example.transaction_service.repository.AccountTypeRepository;
import com.example.transaction_service.repository.ClientRepository;
import com.example.transaction_service.service.account.AbstractCreditAccountService;
import com.example.transaction_service.service.common.aop.annotation.Cached;
import com.example.transaction_service.service.common.aop.annotation.LogDatasourceError;
import org.hibernate.annotations.Cache;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * Класс, выполняющий действия, связанные с клиентскими аккаунтами {@link Account} типа <b>CREDIT</b>
 */
@Service
public class CreditAccountServiceManager extends AbstractCreditAccountService<Account> {
    private final ClientRepository clientRepository;
    private final AccountTypeRepository accountTypeRepository;
    private final AccountEnvironment accountEnvironment;

    public CreditAccountServiceManager(AccountRepository accountRepository, ClientRepository clientRepository, AccountTypeRepository accountTypeRepository, AccountEnvironment accountEnvironment) {
        super(accountRepository);
        this.clientRepository = clientRepository;
        this.accountTypeRepository = accountTypeRepository;
        this.accountEnvironment = accountEnvironment;
    }

    @Override
    @LogDatasourceError
    public void save(UUID clientId, long accountTypeId) {
        AccountType accountType = accountTypeRepository.findById(accountTypeId).orElseThrow(() -> new NotFoundException(String.format("unknown account type id\naccount type id : %s", accountTypeId)));
        Client client = clientRepository.findByClientId(clientId).orElseThrow(() -> new NotFoundException(String.format("client not found by id\nid : %s", clientId)));
        if (accountType.getId() == AccountTypeEnumeration.CREDIT.getId() && accountRepository.findAccountCountByClientIdAndAccountTypeId(clientId, AccountTypeEnumeration.CREDIT.getId()) < 1) {
            Account account = new Account(client, accountType);
            account.setBalance(accountEnvironment.getACCOUNT_CREDIT_START_BALANCE());
            accountRepository.save(account);
        } else {
            throw new InvalidDataException(String.format("account can not be saved as credit one\naccount type : %s", accountType));
        }
    }

    @Override
    public Account getById(long id) {
        return accountRepository.findAccountById(id).orElseThrow(() -> new NotFoundException(String.format("client by id is not found\nid : %s", id)));
    }

    @Override
    public List<Account> getByClientIdAndAccountType(UUID clientId){
        return accountRepository.findAccountByClientIdAndAccountTypeId(clientId, AccountTypeEnumeration.CREDIT.getId());
    }
}
