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
    @Query("from Client c where c.client_id=:clientId")
    Optional<Client> findByClientId(@Param("clientId") UUID clientId);

    @Query("select case when exists(from Client where client_id=:clientId) then true else false end")
    boolean existsClientByClientId(@Param("clientId") UUID clientId);

    Optional<Client> findByUserId();
}
