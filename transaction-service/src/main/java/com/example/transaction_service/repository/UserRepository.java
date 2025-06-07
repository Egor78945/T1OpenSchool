package com.example.transaction_service.repository;

import com.example.transaction_service.model.user.entity.User;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Query("from User where client_id.id=:clientId")
    User findByClient_id(@Param("clientId") long clientId);
    User findByEmail(String email);
    @Query("select case when exists(from User where email=:email) then true else false end")
    boolean existsUserByEmail(@Param("email") String email);
    @Query("select case when exists(from User where client_id.id=:clientId) then true else false end")
    boolean existsUserByClient_id(@Param("clientId") long clientId);
}
