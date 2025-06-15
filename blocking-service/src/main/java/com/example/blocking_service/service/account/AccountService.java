package com.example.blocking_service.service.account;

import com.example.blocking_service.model.account.entity.Account;
import com.example.blocking_service.service.common.EntityService;

import java.util.UUID;

public interface AccountService<A extends Account> extends EntityService<A> {
    A update(A account);
    A getById(long id);
    A getByNaturalId(UUID accountId);
    void changeStatusByNaturalIdAndStatusId(UUID naturalId, long statusId);
    boolean existsByNaturalId(UUID accountId);
    boolean existsById(long id);
}
