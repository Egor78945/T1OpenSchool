package com.example.blocking_service.repository;

import com.example.blocking_service.model.client.entity.Client;
import com.example.blocking_service.model.client.status.entity.ClientStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
    @Modifying
    @Query("update Client set client_status_id=(from ClientStatus where id=:clientStatusId) where client_id=:clientId")
    void updateClientStatusByClient_idAndClientStatus(@Param("clientId") UUID clientId, @Param("clientStatusId") long clientStatusId);

    @Query("select case when exists(from Client where client_id=:accountId) then true else false end")
    boolean existsClientByClientId(@Param("clientId") UUID clientId);

    @Query("from Client where client_id=:clientId")
    Optional<Client> findClientByClient_id(@Param("clientId") UUID clientId);
}
