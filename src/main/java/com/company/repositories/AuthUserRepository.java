package com.company.repositories;

import com.company.entity.AuthUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface AuthUserRepository extends JpaRepository<AuthUser, String> {
    @Query("select a from AuthUser a where a.phoneNumber = ?1")
    Optional<AuthUser> findByPhoneNumber(String phoneNumber);
}