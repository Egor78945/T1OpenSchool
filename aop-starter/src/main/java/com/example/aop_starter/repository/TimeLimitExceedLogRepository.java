package com.example.aop_starter.repository;

import com.example.aop_starter.model.log.entity.TimeLimitExceedLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TimeLimitExceedLogRepository extends JpaRepository<TimeLimitExceedLog, Long> {
}
