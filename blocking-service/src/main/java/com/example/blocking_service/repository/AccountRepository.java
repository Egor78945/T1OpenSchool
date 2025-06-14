package com.example.blocking_service.repository;

import com.example.blocking_service.model.account.entity.Account;
import com.example.blocking_service.model.account.status.entity.AccountStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    @Query("update Account set accountStatus=:accountStatus where accountId=:accountId")
    void updateAccountStatusByAccount_idAndAccountStatus(@Param("accountId") UUID accountId, @Param("accountStatus")AccountStatus accountStatus);
}
