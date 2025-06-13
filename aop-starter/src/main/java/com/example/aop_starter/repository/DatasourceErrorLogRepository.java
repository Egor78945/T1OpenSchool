package com.example.aop_starter.repository;

import com.example.aop_starter.model.log.entity.DatasourceErrorLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DatasourceErrorLogRepository extends JpaRepository<DatasourceErrorLog, Long> {
}
