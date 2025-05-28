package com.example.transaction_service.service.account;

import com.example.transaction_service.model.account.entity.Account;
import com.example.transaction_service.repository.AccountRepository;

import java.util.List;
import java.util.UUID;

/**
 * Абстрактный класс, выполняющий действия, связанные с клиентскими аккаунтами {@link Account} типа <b>CREDIT</b>
 * @param <A> Тип, являющийся пользовательским аккаунтом {@link Account} или его наследником
 */
public abstract class AbstractCreditAccountService<A extends Account> extends AbstractAccountService<A> {

    public AbstractCreditAccountService(AccountRepository accountRepository) {
        super(accountRepository);
    }

    @Override
    public abstract void save(UUID clientId, long accountTypeId);

    @Override
    public abstract A getById(long id);

    @Override
    public abstract List<A> getByClientIdAndAccountType(UUID clientId);
}
