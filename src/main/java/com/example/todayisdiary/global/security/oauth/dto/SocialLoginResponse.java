package com.example.todayisdiary.global.security.oauth.dto;

import com.example.todayisdiary.domain.user.enums.Role;
import com.example.todayisdiary.global.security.oauth.entity.ProviderType;
import lombok.Builder;
import lombok.Data;

@Data
public class SocialLoginResponse {
    private final Boolean newUser;
    private final String name;
    private final String email;
    private final String atk;
    private final String rtk;
    private final Role role;
    private final ProviderType providerType;

    @Builder
    public SocialLoginResponse (String name, String email,String atk, String rtk, Boolean newUser, ProviderType providerType) {
        this.name = name;
        this.email = email;
        this.atk = atk;
        this.rtk = rtk;
        this.newUser = newUser;
        this.role = Role.ROLE_USER;
        this.providerType = providerType;
    }
}
