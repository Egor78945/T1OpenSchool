package com.example.transaction_service.model.account.type.enumeration;

import com.example.transaction_service.exception.NotFoundException;

import java.util.Map;
import java.util.Optional;

/**
 * Перечисление типов клиентского аккаунта
 */
public enum AccountTypeEnumeration {
    DEBIT(1), CREDIT(2);
    private final long id;
    private static final Map<String, AccountTypeEnumeration> accountTypeEnumerationByName;
    private static final Map<Long, AccountTypeEnumeration> accountTypeEnumerationById;

    AccountTypeEnumeration(long id) {
        this.id = id;
    }

    static {
        accountTypeEnumerationByName = Map.of(DEBIT.name(), DEBIT, CREDIT.name(), CREDIT);
        accountTypeEnumerationById = Map.of(DEBIT.getId(), DEBIT, CREDIT.getId(), CREDIT);
    }

    public long getId() {
        return id;
    }

    public static AccountTypeEnumeration getById(long id) {
        return Optional.ofNullable(accountTypeEnumerationById.get(id)).orElseThrow(() -> new NotFoundException(String.format("unknown account type enumeration by id\naccount type id : %s", id)));
    }

    public static AccountTypeEnumeration getByName(String name) {
        return Optional.ofNullable(accountTypeEnumerationByName.get(name)).orElseThrow(() -> new NotFoundException(String.format("unknown account type enumeration by name\naccount type name : %s", name)));
    }
}
