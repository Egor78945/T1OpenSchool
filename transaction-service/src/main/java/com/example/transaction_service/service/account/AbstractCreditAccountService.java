package com.example.transaction_service.service.account;

import com.example.transaction_service.model.account.entity.Account;
import com.example.transaction_service.model.account.status.entity.AccountStatus;
import com.example.transaction_service.model.account.type.entity.AccountType;
import com.example.transaction_service.repository.AccountRepository;
import com.example.transaction_service.repository.AccountStatusRepository;
import com.example.transaction_service.repository.AccountTypeRepository;

import java.util.List;
import java.util.UUID;

/**
 * Абстрактный класс, выполняющий действия, связанные с клиентскими аккаунтами {@link Account} типа <b>CREDIT</b>
 * @param <A> Тип, являющийся пользовательским аккаунтом {@link Account} или его наследником
 */
public abstract class AbstractCreditAccountService<A extends Account> extends AbstractAccountService<A> {

    public AbstractCreditAccountService(AccountRepository accountRepository, AccountTypeRepository accountTypeRepository, AccountStatusRepository accountStatusRepository) {
        super(accountRepository, accountTypeRepository, accountStatusRepository);
    }

    @Override
    public abstract UUID save(UUID clientId, AccountType accountType, AccountStatus accountStatus);

    @Override
    public abstract A update(A account);

    @Override
    public abstract List<A> getByClientIdAndAccountType(UUID clientId);
}
