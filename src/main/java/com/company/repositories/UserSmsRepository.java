package com.company.repositories;

import com.company.entity.UserSms;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserSmsRepository extends JpaRepository<UserSms, Integer> {
    @Query("select u from UserSms u where u.userId = ?1 and u.toTime>CURRENT_TIMESTAMP ")
    Optional<UserSms> findByUserId(String userId);
}