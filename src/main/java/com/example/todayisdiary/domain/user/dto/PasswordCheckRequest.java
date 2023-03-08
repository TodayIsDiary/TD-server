package com.example.todayisdiary.domain.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PasswordCheckRequest {
    private String code;
    private String email;
}
