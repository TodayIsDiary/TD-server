package com.example.todayisdiary.domain.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CheckRequest {
    private String email;

    // 메일 인증 코드
    private String code;
}
