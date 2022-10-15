package com.example.todayisdiary.global.security.jwt.repository;

import com.example.todayisdiary.global.security.jwt.entity.RefreshToken;
import org.springframework.data.repository.CrudRepository;

public interface RefreshTokenRepository extends CrudRepository<RefreshToken, String> {
    boolean existsByAccountId(String accountId);
}
