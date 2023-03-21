package com.example.todayisdiary.global.security.oauth.entity;

import com.example.todayisdiary.global.security.oauth.dto.GoogleOAuthTokenDto;
import com.example.todayisdiary.global.security.oauth.dto.GoogleUserInfoDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class GoogleOauth {

    private final String googleLoginUrl = "https://accounts.google.com";
    private final String google_token_request_url = "https://oauth2.googleapis.com/token";
    private final String google_userInfo_request_url = "https://www.googleapis.com/oauth2/v1/userinfo";
    private final ObjectMapper objectMapper;

    private final WebClient webClient;

    @Value("${spring.security.oauth2.client.registration.google.clientId}")
    private String googleClientId;

    @Value("${spring.security.oauth2.client.registration.google.redirectUri}")
    private String googleRedirectUrl;

    @Value("${spring.security.oauth2.client.registration.google.clientSecret}")
    private String googleSecret;

    public String getOauthRedirectURL() {
        String reqUrl = googleLoginUrl + "/o/oauth2/v2/auth?client_id=" + googleClientId + "&redirect_uri=" + googleRedirectUrl
                + "&response_type=code&scope=email%20profile%20openid&access_type=offline";
        return reqUrl;
    }

    // 일회용 코드를 보내서 토큰을 포함한 Json이 담긴 ResponseEntity를 받아옴
    public Mono<ResponseEntity<String>> requestAccessToken(String code) {
        WebClient webClient = WebClient.builder().build();

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("code", code);
        params.add("client_id", googleClientId);
        params.add("client_secret", googleSecret);
        params.add("redirect_uri", googleRedirectUrl);
        params.add("grant_type", "authorization_code");

        return webClient.post()
                .uri(google_token_request_url)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(BodyInserters.fromFormData(params))
                .retrieve()
                .toEntity(String.class)
                .flatMap(responseEntity -> {
                    if (responseEntity.getStatusCode() == HttpStatus.OK) {
                        return Mono.just(responseEntity);
                    }
                    return Mono.empty();
                });
    }

    public GoogleOAuthTokenDto getAccessToken(Mono<ResponseEntity<String>> response) throws JsonProcessingException {
        GoogleOAuthTokenDto googleOAuthTokenDto = objectMapper.readValue(response.toString(), GoogleOAuthTokenDto.class);
        return googleOAuthTokenDto;
    }

    public Flux<String> requestUserInfo(GoogleOAuthTokenDto oAuthToken) {

        return webClient.get()
                .uri(google_userInfo_request_url)
                .header(HttpHeaders.AUTHORIZATION, "Bearer" + oAuthToken.getAccess_token())
                .retrieve() // body를 받아 디코딩하는 간단한 메소드
                .bodyToFlux(String.class);
    }

    public GoogleUserInfoDto getUserInfo(Flux<String> response) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(response.toString() , GoogleUserInfoDto.class);
    }

}
