package com.example.todayisdiary.domain.user.dto;

import com.example.todayisdiary.domain.user.enums.Sex;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SignupRequest {
    // 아이디
    private String accountId;

    // 닉네임
    private String nickName;

    private String email;

    private String password;

    // 메일 인증 코드
    private String code;

    private Sex sex;

    private String introduction;
}
