package com.example.transaction_service.service.account.implementation;

import com.example.transaction_service.exception.InvalidDataException;
import com.example.transaction_service.exception.NotFoundException;
import com.example.transaction_service.model.account.entity.Account;
import com.example.transaction_service.model.account.type.entity.AccountType;
import com.example.transaction_service.model.account.type.enumeration.AccountTypeEnumeration;
import com.example.transaction_service.model.client.entity.Client;
import com.example.transaction_service.repository.AccountRepository;
import com.example.transaction_service.repository.AccountTypeRepository;
import com.example.transaction_service.repository.ClientRepository;
import com.example.transaction_service.service.account.AbstractDebitAccountService;
import com.example.transaction_service.service.common.aop.annotation.Cached;
import com.example.transaction_service.service.common.aop.annotation.LogDatasourceError;
import com.example.transaction_service.service.common.aop.annotation.Metric;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * Класс, выполняющий действия, связанные с клиентскими аккаунтами {@link Account} типа <b>DEBIT</b>
 */
@Service
public class DebitAccountServiceManager extends AbstractDebitAccountService<Account> {
    private final ClientRepository clientRepository;
    private final AccountTypeRepository accountTypeRepository;

    public DebitAccountServiceManager(AccountRepository accountRepository, ClientRepository clientRepository, AccountTypeRepository accountTypeRepository) {
        super(accountRepository);
        this.clientRepository = clientRepository;
        this.accountTypeRepository = accountTypeRepository;
    }

    @Override
    @LogDatasourceError
    @Metric
    public void save(UUID clientId, long accountTypeId) {
        AccountType accountType = accountTypeRepository.findById(accountTypeId).orElseThrow(() -> new NotFoundException(String.format("unknown account type id\naccount type id : %s", accountTypeId)));
        Client client = clientRepository.findByClientId(clientId).orElseThrow(() -> new NotFoundException(String.format("client not found by id\nid : %s", clientId)));
        if (accountType.getId() == AccountTypeEnumeration.DEBIT.getId() && accountRepository.findAccountCountByClientIdAndAccountTypeId(clientId, AccountTypeEnumeration.DEBIT.getId()) < 1) {
            Account account = new Account(client, buildUUID(), accountType);
            accountRepository.save(account);
        } else {
            throw new InvalidDataException(String.format("account can not be saved as debit one\naccount type : %s", accountType));
        }
    }

    @Override
    public Account getById(long id) {
        return accountRepository.findAccountById(id).orElseThrow(() -> new NotFoundException(String.format("client by id is not found\nid : %s", id)));
    }

    @Override
    public List<Account> getByClientIdAndAccountType(UUID clientId){
        return accountRepository.findAccountByClientIdAndAccountTypeId(clientId, AccountTypeEnumeration.DEBIT.getId());
    }
}
