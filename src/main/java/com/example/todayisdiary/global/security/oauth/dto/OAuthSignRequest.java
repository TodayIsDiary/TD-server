package com.example.todayisdiary.global.security.oauth.dto;

import com.example.todayisdiary.domain.user.enums.Sex;
import com.example.todayisdiary.global.security.oauth.entity.ProviderType;
import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public class OAuthSignRequest {
    // 아이디
    private String accountId;

    // 닉네임
    private String nickName;

    private String email;

    private String imageUrl;

    private Sex sex;

    private String introduction;

    private ProviderType providerType;
}
