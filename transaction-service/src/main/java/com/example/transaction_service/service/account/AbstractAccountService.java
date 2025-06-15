package com.example.transaction_service.service.account;

import com.example.transaction_service.exception.NotFoundException;
import com.example.transaction_service.exception.ProcessingException;
import com.example.transaction_service.model.account.entity.Account;
import com.example.transaction_service.model.account.status.entity.AccountStatus;
import com.example.transaction_service.model.account.type.entity.AccountType;
import com.example.transaction_service.repository.AccountRepository;
import com.example.transaction_service.model.client.entity.Client;
import com.example.transaction_service.model.account.type.enumeration.AccountTypeEnumeration;
import com.example.transaction_service.repository.AccountStatusRepository;
import com.example.transaction_service.repository.AccountTypeRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

/**
 * Абстрактный класс, выполняющий действия, связанные с пользовательскими аккаунтами
 * @param <A> Тип, являющийся пользовательским аккаунтом {@link Account} или его наследником
 */
public abstract class AbstractAccountService<A extends Account> {
    protected AccountRepository accountRepository;
    protected AccountTypeRepository accountTypeRepository;
    protected AccountStatusRepository accountStatusRepository;

    public AbstractAccountService(AccountRepository accountRepository, AccountTypeRepository accountTypeRepository, AccountStatusRepository accountStatusRepository) {
        this.accountRepository = accountRepository;
        this.accountTypeRepository = accountTypeRepository;
        this.accountStatusRepository = accountStatusRepository;
    }

    /**
     * Создать и сохранить новый несуществующий клиентский аккаунт {@link Account}
     * @param clientId Id существующего клиента {@link Client}
     * @param accountType Тип клиентского аккаунта {@link AccountType}
     * @param accountStatus Статус клиентского аккаунта {@link AccountStatus}
     */
    public abstract UUID save(UUID clientId, AccountType accountType, AccountStatus accountStatus);

    public abstract A update(A account);

    /**
     * Получить список {@link List} существующих клиентских аккаунтов {@link Account} по Id и типу клиентского аккаунта {@link AccountTypeEnumeration}
     * @param clientId Id существующего клиента
     * @return Список {@link List} существующих клиентских аккаунтов {@link Account} определённого клиента {@link Client} по его Id и типу клиентского аккаунта {@link AccountTypeEnumeration}
     */
    public abstract List<A> getByClientIdAndAccountType(UUID clientId);

    /**
     * Получить список {@link List} существующих клиентских аккаунтов {@link Account} по Id клиентского аккаунта {@link Client}
     * @param id Id существующего клиента
     * @return Список {@link List} существующих клиентских аккаунтов {@link Account} определённого клиента {@link Client} по его Id
     */
    public List<Account> getByClientId(UUID id) {
        return accountRepository.findAccountByClientId(id);
    }

    /**
     * Получить существующий клиентский аккаунт {@link Account} по его Id
     * @param id Id существующего клиентского аккаунта
     * @return Существующий клиентский аккаунт {@link Account}
     */
    public Account getById(long id) {
        return accountRepository.findAccountById(id).orElseThrow(() -> new NotFoundException(String.format("account by client id is not found\nid : %s", id)));
    }

    public Account getByAccountId(UUID id) {
        return accountRepository.findAccountByAccountId(id).orElseThrow(() -> new NotFoundException(String.format("account by account id is not found\naccount id : %s", id)));
    }

    public List<Account> getByAccountStatusIdAndCount(long accountStatusId, int count){
        return accountRepository.findAccountByAccountStatusIdAndLimit(accountStatusId, PageRequest.ofSize(count));
    }

    public UUID buildUUID(){
        UUID uuid = UUID.randomUUID();
        for(int i = 0; i < 10; i++){
            if(accountRepository.existsAccountByAccountId(uuid)){
                uuid = UUID.randomUUID();
            } else {
                return uuid;
            }
        }
        throw new ProcessingException("uuid is not generated");
    }
}
