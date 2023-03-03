package com.example.todayisdiary.global.security.oauth.service;

import com.example.todayisdiary.domain.user.dto.UserResponse;
import com.example.todayisdiary.domain.user.entity.User;
import com.example.todayisdiary.domain.user.enums.Role;
import com.example.todayisdiary.domain.user.facade.UserFacade;
import com.example.todayisdiary.domain.user.repository.UserRepository;
import com.example.todayisdiary.global.security.jwt.JwtProvider;
import com.example.todayisdiary.global.security.oauth.dto.*;
import com.example.todayisdiary.global.security.oauth.entity.GoogleOauth;
import com.example.todayisdiary.global.security.oauth.entity.KakaoOauth;
import com.example.todayisdiary.global.security.oauth.entity.ProviderType;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class OAuthServiceImpl implements OAuthService{
    private final GoogleOauth googleOAuth;
    private final KakaoOauth kakaoOAuth;
    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;
    private final UserFacade userFacade;

    @Override
    public ResponseEntity<SocialLoginResponse> Login(String email, String name) {
        ProviderType p = ProviderType.LOCAL;

        if (email.contains("gmail") || (email.contains("dsm"))) {
            p = ProviderType.GOOGLE;
        }else if(email.contains("daum") || (email.contains("kakao"))) {
            p = ProviderType.KAKAO;
        }

        String atk = jwtProvider.createAtk(UserResponse.snsOf(email,name, Role.ROLE_USER));
        String rtk = jwtProvider.createRtk(UserResponse.snsOf(email,name, Role.ROLE_USER));

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization", atk);

        return new ResponseEntity<>(new SocialLoginResponse(
                name, email, atk, rtk, false, p
        ), HttpStatus.OK);
    }

    @Override
    public GoogleUserInfoDto getGoogleUserInfoDto(String code) throws JsonProcessingException {
        ResponseEntity<String> accessTokenResponse = googleOAuth.requestAccessToken(code);
        GoogleOAuthTokenDto oAuthToken = googleOAuth.getAccessToken(accessTokenResponse);
        ResponseEntity<String> userInfoResponse = googleOAuth.requestUserInfo(oAuthToken);
        GoogleUserInfoDto googleUser = googleOAuth.getUserInfo(userInfoResponse);
        return googleUser;
    }

    @Override
    public KakaoUserInfoDto getKakaoUserInfoDto(String code) throws JsonProcessingException {
        ResponseEntity<String> accessTokenResponse = kakaoOAuth.requestAccessToken(code);
        KakaoOAuthTokenDto oAuthToken = kakaoOAuth.getAccessToken(accessTokenResponse);
        ResponseEntity<String> userInfoResponse = kakaoOAuth.requestUserInfo(oAuthToken);
        KakaoUserInfoDto kakaoUser = kakaoOAuth.getUserInfo(userInfoResponse);
        return kakaoUser;
    }

    @Override
    @Transactional
    public ResponseEntity<SocialLoginResponse> googlelogin(String code) throws IOException {
        GoogleUserInfoDto googleUser = getGoogleUserInfoDto(code);
        String email = googleUser.getEmail();
        String name = googleUser.getName();
        // 첫 로그인시 사용자 정보를 보내줌
        if (!userRepository.existsByEmail(email)) {
            return new ResponseEntity<>(SocialLoginResponse.builder()
                    .name(name)
                    .email(email)
                    .atk(null)
                    .rtk(null)
                    .newUser(true)
                    .providerType(ProviderType.GOOGLE).build(),HttpStatus.OK);
        }
        // 이메일이 존재할시 로그인
        User user = userFacade.getUserByEmail(email);
        if(user.getProviderType().equals(ProviderType.LOCAL)){
          throw new IllegalArgumentException("이미 가입된 계정입니다.");
        }else return Login(email, name);
    }

    @Override
    @Transactional
    public ResponseEntity<SocialLoginResponse> kakaoLogin(String code) throws IOException {
        KakaoUserInfoDto kakaoUser = getKakaoUserInfoDto(code);
        String email = kakaoUser.getKakao_account().getEmail();
        String name = kakaoUser.getProperties().getNickname();

        // 첫 로그인시 사용자 정보를 보내줌
        if (!userRepository.existsByEmail(email)) {
            return new ResponseEntity<>(SocialLoginResponse.builder()
                    .name(name)
                    .email(email)
                    .atk(null)
                    .rtk(null)
                    .newUser(true)
                    .providerType(ProviderType.KAKAO).build(),HttpStatus.OK);
        }
        // 이메일이 존재할시 로그인
        User user = userFacade.getUserByEmail(email);
        if(user.getProviderType().equals(ProviderType.LOCAL)){
            throw new IllegalArgumentException("이미 가입된 계정입니다.");
        }else return Login(email, name);
    }

    @Override
    public ResponseEntity<RegisterResponse> socialRegister(OAuthSignRequest request) {

        userRepository.save(
                User.builder()
                        .accountId(request.getAccountId())
                        .nickName(request.getNickName())
                        .email(request.getEmail())
                        .password(null)
                        .sex(request.getSex())
                        .introduction(request.getIntroduction())
                        .imageUrl(defaultImage(request.getImageUrl()))
                        .providerType(request.getProviderType())
                        .build()
        );

        String atk = jwtProvider.createAtk(UserResponse.snsOf(request.getEmail(),request.getAccountId(), Role.ROLE_USER));
        String rtk = jwtProvider.createRtk(UserResponse.snsOf(request.getEmail(), request.getAccountId(), Role.ROLE_USER));


        return new ResponseEntity<>(new RegisterResponse(
                request.getAccountId(),
                request.getEmail(),
                atk,
                rtk
        ), HttpStatus.OK);
    }

    private String defaultImage(String imageUrl){
        if(Objects.equals(imageUrl, "null")){return "4f743a16-e96f-49e7-9c11-0948592dab18-5087579.png";}
        else return imageUrl == null ? "4f743a16-e96f-49e7-9c11-0948592dab18-5087579.png" : imageUrl;
    }
}
