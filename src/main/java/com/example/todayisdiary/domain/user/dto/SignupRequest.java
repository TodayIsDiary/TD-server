package com.example.todayisdiary.domain.user.dto;

import com.example.todayisdiary.domain.user.enums.Sex;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.Pattern;

@Getter
@AllArgsConstructor
public class SignupRequest {
    // 아이디
    private String accountId;

    // 닉네임
    private String nickName;

    private String email;

    @Pattern(regexp="^(?=.*[a-zA-Z])(?=.*\\\\d)(?=.*\\\\W).{8,20}$",
            message = "비밀번호는 영문과 특수문자 숫자를 포함하여 8자 이상이어야 합니다.")
    private String password;

    // 메일 인증 코드
    private String code;

    private Sex sex;

    private String introduction;
}
