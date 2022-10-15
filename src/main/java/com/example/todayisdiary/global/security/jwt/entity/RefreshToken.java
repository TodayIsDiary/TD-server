package com.example.todayisdiary.global.security.jwt.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@Getter
@AllArgsConstructor
@Builder
@RedisHash
public class RefreshToken {

    @Id
    private String accountId;

    private String refreshToken;

    private Long rtkTime;
}
