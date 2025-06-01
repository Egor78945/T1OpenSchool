package com.example.transaction_service.repository;

import com.example.transaction_service.model.account.status.entity.AccountStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountStatusRepository extends JpaRepository<AccountStatus, Long> {
}
