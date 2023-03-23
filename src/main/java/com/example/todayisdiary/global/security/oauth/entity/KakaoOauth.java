package com.example.todayisdiary.global.security.oauth.entity;

import com.example.todayisdiary.global.security.oauth.dto.KakaoOAuthTokenDto;
import com.example.todayisdiary.global.security.oauth.dto.KakaoUserInfoDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Configuration
@RequiredArgsConstructor
public class KakaoOauth {

    @Value("${spring.security.oauth2.client.registration.kakao.clientSecret}")
    private String kakaoClientSecret;

    @Value("${spring.security.oauth2.client.registration.kakao.redirectUri}")
    private String kakaoRedirectUrl;

    private final String kakao_token_request_url =  "https://kauth.kakao.com/oauth/token";

    public String responseUrl() {
        String kakaoLoginUrl = "https://kauth.kakao.com/oauth/authorize?client_id=" + kakaoClientSecret +
                "&redirect_uri=" + kakaoRedirectUrl + "&response_type=code";
        return kakaoLoginUrl;
    }

    public Mono<ResponseEntity<String>> requestAccessToken(String code) {
        WebClient webClient = WebClient.builder().build();

        HttpHeaders headersAccess = new HttpHeaders();
        headersAccess.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", kakaoClientSecret);
        params.add("redirect_uri", kakaoRedirectUrl);
        params.add("code", code);

        return webClient.post()
                .uri(kakao_token_request_url)
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

    public KakaoOAuthTokenDto getAccessToken(Mono<ResponseEntity<String>> response) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        KakaoOAuthTokenDto kakaoOAuthTokenDto = objectMapper.readValue(response.toString(), KakaoOAuthTokenDto.class);
        return kakaoOAuthTokenDto;
    }

    public Flux<String> requestUserInfo(KakaoOAuthTokenDto oAuthToken) {
        WebClient webClient = WebClient.builder().build();

        return webClient.get()
                .uri("https://kapi.kakao.com/v2/user/me")
                .header(HttpHeaders.AUTHORIZATION, "Bearer" + oAuthToken.getAccess_token())
                .retrieve()
                .bodyToFlux(String.class);
    }

    public KakaoUserInfoDto getUserInfo(Flux<String> response) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(response.toString(), KakaoUserInfoDto.class);
    }
}
