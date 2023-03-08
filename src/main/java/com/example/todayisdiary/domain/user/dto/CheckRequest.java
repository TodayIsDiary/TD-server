package com.example.todayisdiary.domain.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.Pattern;

@AllArgsConstructor
@Getter
public class CheckRequest {
    private String email;

    private Boolean chek;

    @Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,16}",
            message = "비밀번호는 8~16자 영문 대 소문자, 숫자, 특수문자를 사용하세요.")
    private String password;

    private String passwordValid;

    // 메일 인증 코드
    private String code;
}
