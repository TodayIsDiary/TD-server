package com.example.todayisdiary.domain.user.repository;

import com.example.todayisdiary.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByEmail(String email);

    boolean existsByAccountId(String accountId);

    Optional<User> findUserByAccountId(String accountId);

    Optional<User> findUserByEmail(String email);
}
