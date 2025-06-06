package com.example.transaction_service.model.account.status.entity.enumeration;

import java.util.Map;
import java.util.Optional;

public enum AccountStatusEnumeration {
    ARRESTED(1), BLOCKED(2), CLOSED(3), OPEN(4);
    private long id;
    private static Map<Long, AccountStatusEnumeration> accountStatusEnumerationById;

    AccountStatusEnumeration(long id) {
        this.id = id;
    }

    static {
        accountStatusEnumerationById = Map.of(1L, ARRESTED, 2L, BLOCKED, 3L, CLOSED, 4L, OPEN);
    }

    public long getId() {
        return id;
    }

    public static Optional<AccountStatusEnumeration> getById(long id){
        return Optional.ofNullable(accountStatusEnumerationById.get(id));
    }
}
