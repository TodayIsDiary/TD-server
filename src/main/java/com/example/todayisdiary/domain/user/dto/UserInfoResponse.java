package com.example.todayisdiary.domain.user.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserInfoResponse {
    private final String nickName;
    private final String introduction;

}
