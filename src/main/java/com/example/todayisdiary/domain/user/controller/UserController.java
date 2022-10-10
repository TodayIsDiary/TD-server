package com.example.todayisdiary.domain.user.controller;

import com.example.todayisdiary.domain.user.dto.LoginRequest;
import com.example.todayisdiary.domain.user.dto.SignupRequest;
import com.example.todayisdiary.domain.user.dto.UserResponse;
import com.example.todayisdiary.domain.user.service.UserService;
import com.example.todayisdiary.global.mail.dto.MailDto;
import com.example.todayisdiary.global.mail.service.MailService;
import com.example.todayisdiary.global.security.auth.AuthDetails;
import com.example.todayisdiary.global.security.jwt.JwtProvider;
import com.example.todayisdiary.global.security.jwt.dto.TokenResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    public final JwtProvider jwtProvider;
    private final MailService mailService;

    @PostMapping("/signup")
    public UserResponse signUp(@RequestBody SignupRequest request){ return userService.signup(request);}

    @PostMapping("/login")
    public TokenResponse login(@RequestBody LoginRequest request){
        UserResponse userResponse = userService.login(request);
        return jwtProvider.createTokenByLogin(userResponse);
    }

    @GetMapping("/reissue")
    public TokenResponse reissue(
            @AuthenticationPrincipal AuthDetails authDetails
    ){
        UserResponse userResponse = UserResponse.of(authDetails.getUser());
        return jwtProvider.reissueAtk(userResponse);
    }

    @PostMapping("/lost/password")
    public String mail(@RequestBody MailDto dto){
        mailService.mailSend(dto);
        return "메일이 정상적으로 보내줬습니다.";
    }
}
