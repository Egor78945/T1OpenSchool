package com.example.blocking_service.repository;

import com.example.blocking_service.model.client.status.entity.ClientStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientStatusRepository extends JpaRepository<ClientStatus, Long> {
}
