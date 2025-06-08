package com.example.transaction_service.repository;

import com.example.transaction_service.model.user.role.entity.UserRole;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, Long> {
    @Query("from UserRole ur " +
            "join fetch ur.user_id u " +
            "join fetch ur.role_id r " +
            "where u.id=:userId")
    List<UserRole> findAllByUserId(@Param("userId") long userId);

    @Query("from UserRole ur " +
            "join fetch ur.user_id u " +
            "join fetch ur.role_id r " +
            "where u.email=:userEmail")
    List<UserRole> findAllByUserEmail(@Param("userEmail") String userEmail);
}
