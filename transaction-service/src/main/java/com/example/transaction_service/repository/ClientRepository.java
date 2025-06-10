package com.example.transaction_service.repository;

import com.example.transaction_service.model.client.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
    @Query("from Client c " +
            "join fetch c.user_id " +
            "join fetch c.client_status_id " +
            "where c.client_id=:clientId")
    Optional<Client> findByClientId(@Param("clientId") UUID clientId);

    @Query("select case when exists(from Client where client_id=:clientId) then true else false end")
    boolean existsClientByClientId(@Param("clientId") UUID clientId);

    @Query("from Client c " +
            "join fetch c.user_id u " +
            "join fetch c.client_status_id s " +
            "where u.id=:userId")
    Optional<Client> findByUserId(@Param("userId") long userId);

    @Query("from Client c " +
            "join fetch c.user_id u " +
            "join fetch c.client_status_id " +
            "where u.email=:userEmail")
    Optional<Client> findByUserEmail(@Param("userEmail") String userEmail);

    @Query("from Account a " +
            "join fetch Client c " +
            "join fetch User u " +
            "where a.accountId = :accountId and a.client.client_id=c.client_id")
    Optional<Client> findClientByAccountId(@Param("accountId") long accountId);
}
