package com.example.transaction_service.service.account.implementation;

import com.example.transaction_service.exception.InvalidDataException;
import com.example.transaction_service.model.account.entity.Account;
import com.example.transaction_service.model.account.status.entity.AccountStatus;
import com.example.transaction_service.model.account.type.entity.AccountType;
import com.example.transaction_service.model.account.type.enumeration.AccountTypeEnumeration;
import com.example.transaction_service.model.client.entity.Client;
import com.example.transaction_service.repository.AccountRepository;
import com.example.transaction_service.repository.AccountStatusRepository;
import com.example.transaction_service.repository.AccountTypeRepository;
import com.example.transaction_service.service.account.AbstractDebitAccountService;
import com.example.transaction_service.service.client.AbstractClientService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * Класс, выполняющий действия, связанные с клиентскими аккаунтами {@link Account} типа <b>DEBIT</b>
 */
@Service
public class DebitAccountServiceManager extends AbstractDebitAccountService<Account> {
    private final AbstractClientService<Client> clientService;
    public DebitAccountServiceManager(AccountRepository accountRepository, AbstractClientService<Client> clientService, AccountTypeRepository accountTypeRepository, AccountStatusRepository accountStatusRepository) {
        super(accountRepository, accountTypeRepository, accountStatusRepository);
        this.clientService = clientService;
    }

    @Override
    public UUID save(UUID clientId, AccountType accountType, AccountStatus accountStatus) {
        Client client = clientService.getByClientId(clientId);
        if (accountType.getId() == AccountTypeEnumeration.DEBIT.getId() && accountRepository.findAccountCountByClientIdAndAccountTypeId(clientId, AccountTypeEnumeration.DEBIT.getId()) < 1) {
            Account account = new Account(client, buildUUID(), accountType, accountStatus);
            return accountRepository.save(account).getAccountId();
        } else {
            throw new InvalidDataException(String.format("account can not be saved as debit one\naccount type : %s", accountType));
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
    public List<Account> getByClientIdAndAccountType(UUID clientId){
        return accountRepository.findAccountByClientIdAndAccountTypeId(clientId, AccountTypeEnumeration.DEBIT.getId());
    }
}
