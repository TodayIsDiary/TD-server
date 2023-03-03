package com.example.todayisdiary.domain.user.controller;

import com.example.todayisdiary.global.security.oauth.dto.OAuthSignRequest;
import com.example.todayisdiary.global.security.oauth.dto.RegisterResponse;
import com.example.todayisdiary.global.security.oauth.dto.SocialLoginResponse;
import com.example.todayisdiary.global.security.oauth.entity.GoogleOauth;
import com.example.todayisdiary.global.security.oauth.entity.KakaoOauth;
import com.example.todayisdiary.global.security.oauth.service.OAuthService;
import com.example.todayisdiary.global.security.oauth.service.OAuthServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Tag(name = "OAuth", description = "SNS Login에 대한 API 입니다.")
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class OAuthController {
    private final OAuthService oAuthService;
    private final GoogleOauth googleOauth;
    private final KakaoOauth kakaoOauth;

    @Operation(summary = "구글 로그인&회원가입")
    @GetMapping("/google")
    public void getGoogleAuth(HttpServletResponse response)throws Exception{
        response.sendRedirect(googleOauth.getOauthRedirectURL());
    }

    @Operation(summary = "카카오 로그인&회원가입")
    @GetMapping("/kakao")
    public void getkakaoAuth(HttpServletResponse response)throws Exception{
        response.sendRedirect(kakaoOauth.responseUrl());
    }

    @Operation(summary = "구글 로그인후")
    @GetMapping("/google/login")
    public ResponseEntity<SocialLoginResponse> callback(@RequestParam(name = "code") String code)throws IOException{
        return oAuthService.googlelogin(code);
    }

    @Operation(summary = "카카오 로그인후")
    @GetMapping("/kakao/login")
    public ResponseEntity<SocialLoginResponse> callbackKakao(@RequestParam(name = "code") String code)throws IOException{
        return oAuthService.kakaoLogin(code);
    }

    @Operation(summary = "SNS 로그인 이후 추가 정보 요청")
    @PostMapping("/new/sns")
    public ResponseEntity<RegisterResponse> oauthNewUser(@RequestBody OAuthSignRequest request){
        return oAuthService.socialRegister(request);
    }

}
