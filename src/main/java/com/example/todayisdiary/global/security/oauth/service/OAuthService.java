package com.example.todayisdiary.global.security.oauth.service;

import com.example.todayisdiary.global.security.oauth.dto.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.ResponseEntity;

import java.io.IOException;

public interface OAuthService {
    ResponseEntity<SocialLoginResponse> Login(String email, String name);

    GoogleUserInfoDto getGoogleUserInfoDto(String code) throws JsonProcessingException;

    KakaoUserInfoDto getKakaoUserInfoDto(String code) throws JsonProcessingException;

    ResponseEntity<SocialLoginResponse> googlelogin(String code) throws IOException;

    ResponseEntity<SocialLoginResponse> kakaoLogin(String code) throws IOException;

    ResponseEntity<RegisterResponse> socialRegister(OAuthSignRequest request);
}
