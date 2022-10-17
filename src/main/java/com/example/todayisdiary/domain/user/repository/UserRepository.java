package com.example.todayisdiary.domain.user.repository;

import com.example.todayisdiary.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByEmail(String email);

    boolean existsByAccountId(String accountId);

    Optional<User> findByAccountId(String accountId);

    Optional<User> findByEmail(String email);

    Optional<User> findByCode(String code);

}
