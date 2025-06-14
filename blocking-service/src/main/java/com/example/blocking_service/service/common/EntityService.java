package com.example.blocking_service.service.common;

import java.util.UUID;

public interface EntityService <E> {
    E update(E account);
    E getById(long id);
    E getByNaturalId(UUID naturalId);
    boolean existsByNaturalId(UUID naturalId);
    boolean existsById(long id);
}
