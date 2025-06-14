package com.example.blocking_service.repository;

import com.example.blocking_service.model.client.entity.Client;
import com.example.blocking_service.model.client.status.entity.ClientStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
    @Query("update Client set client_status_id=:clientStatus where client_id=:clientId")
    void updateClientStatusByClient_idAndClientStatus(@Param("clientId") UUID clientId, @Param("clientStatus") ClientStatus clientStatus);
}
