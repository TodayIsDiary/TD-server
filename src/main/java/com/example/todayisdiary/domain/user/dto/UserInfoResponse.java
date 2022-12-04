package com.example.todayisdiary.domain.user.dto;

import com.example.todayisdiary.domain.user.enums.Sex;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserInfoResponse {
    private final String nickName;
    private final String introduction;
    private final int visit;
    private final int love;
    private final int comment;
    private final Sex sex;
    private final String email;
}
