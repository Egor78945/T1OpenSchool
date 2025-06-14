package com.example.blocking_service.repository;

import com.example.blocking_service.model.account.entity.Account;
import com.example.blocking_service.model.account.status.entity.AccountStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    @Modifying
    @Query("update Account set accountStatus=(from AccountStatus where id=:accountStatusId) where accountId=:accountId")
    void updateAccountStatusByAccount_idAndAccountStatusId(@Param("accountId") UUID accountId, @Param("accountStatusId") long accountStatusId);

    @Query("select case when exists(from Account where accountId=:accountId) then true else false end")
    boolean existsAccountByAccountId(@Param("accountId") UUID accountId);

    Optional<Account> findAccountByAccountId(UUID accountId);
}
