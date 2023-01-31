package com.example.todayisdiary.global.security.oauth.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RegisterResponse {
    private final String accountId;
    private final String email;
    private final String atk;
    private final String rtk;

    public RegisterResponse(String accountId, String email, String atk, String rtk){
        this.accountId = accountId;
        this.email = email;
        this.atk = atk;
        this.rtk = rtk;

    }
}
