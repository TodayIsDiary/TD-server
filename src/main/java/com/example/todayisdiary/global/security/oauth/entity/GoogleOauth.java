package com.example.todayisdiary.global.security.oauth.entity;

import com.example.todayisdiary.global.security.oauth.dto.GoogleOAuthTokenDto;
import com.example.todayisdiary.global.security.oauth.dto.GoogleUserInfoDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class GoogleOauth {

    private final String googleLoginUrl = "https://accounts.google.com";
    private final String google_token_request_url = "https://oauth2.googleapis.com/token";
    private final String google_userInfo_request_url = "https://www.googleapis.com/oauth2/v1/userinfo";
    private final ObjectMapper objectMapper;

    private final RestTemplate restTemplate;

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
    public ResponseEntity<String> requestAccessToken(String code) {
        RestTemplate restTemplate = new RestTemplate();
        Map<String, Object> params = new HashMap<>();
        params.put("code", code);
        params.put("client_id", googleClientId);
        params.put("client_secret", googleSecret);
        params.put("redirect_uri", googleRedirectUrl);
        params.put("grant_type", "authorization_code");

        ResponseEntity<String> responseEntity = restTemplate.postForEntity(google_token_request_url,
                params, String.class);

        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            return responseEntity;
        }
        return null;
    }

    public GoogleOAuthTokenDto getAccessToken(ResponseEntity<String> response) throws JsonProcessingException {
        GoogleOAuthTokenDto googleOAuthTokenDto = objectMapper.readValue(response.getBody(), GoogleOAuthTokenDto.class);
        return googleOAuthTokenDto;
    }

    public ResponseEntity<String> requestUserInfo(GoogleOAuthTokenDto oAuthToken) {

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer" + oAuthToken.getAccess_token());

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(google_userInfo_request_url, HttpMethod.GET, request, String.class);
        return response;
    }

    public GoogleUserInfoDto getUserInfo(ResponseEntity<String> response) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        GoogleUserInfoDto googleUserInfoDto = objectMapper.readValue(response.getBody(), GoogleUserInfoDto.class);
        return googleUserInfoDto;
    }

}
