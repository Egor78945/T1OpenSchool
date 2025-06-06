package com.example.transaction_service.repository;

import com.example.transaction_service.model.account.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    @Query("from Account a " +
            "join fetch a.client " +
            "join fetch a.accountType " +
            "where a.id = :id")
    Optional<Account> findAccountById(@Param("id") long id);

    @Query("from Account a " +
            "join fetch a.client " +
            "join fetch a.accountType " +
            "where a.client.client_id = :clientId")
    List<Account> findAccountByClientId(@Param("clientId") UUID clientId);

    @Query("from Account a " +
            "join fetch a.client " +
            "join fetch a.accountType " +
            "where a.client.client_id = :clientId and a.accountType.id = :accountTypeId")
    List<Account> findAccountByClientIdAndAccountTypeId(@Param("clientId") UUID clientId, @Param("accountTypeId") long accountTypeId);

    @Query("select count (*) " +
            "from Account a " +
            "where a.client.client_id = :clientId and a.accountType.id = :accountTypeId")
    int findAccountCountByClientIdAndAccountTypeId(@Param("clientId") UUID clientId, @Param("accountTypeId") long accountTypeId);

    @Query("select case when exists(from Account where accountId=:accountId) then true else false end")
    boolean existsAccountByAccountId(@Param("accountId") UUID accountId);

    @Query("from Account where accountId=:accountId")
    Optional<Account> findAccountByAccountId(@Param("accountId") UUID accountId);
}
