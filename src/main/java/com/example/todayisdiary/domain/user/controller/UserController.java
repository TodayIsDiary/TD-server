package com.example.todayisdiary.domain.user.controller;

import com.example.todayisdiary.domain.user.dto.LoginRequest;
import com.example.todayisdiary.domain.user.dto.PasswordRequest;
import com.example.todayisdiary.domain.user.dto.SignupRequest;
import com.example.todayisdiary.domain.user.dto.UserResponse;
import com.example.todayisdiary.domain.user.service.UserService;
import com.example.todayisdiary.global.mail.dto.MailRequest;
import com.example.todayisdiary.global.security.auth.AuthDetails;
import com.example.todayisdiary.global.security.jwt.JwtProvider;
import com.example.todayisdiary.global.security.jwt.dto.TokenResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Tag(name = "user", description = "유저에 대한 API 입니다.")
@RequiredArgsConstructor
@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    public final JwtProvider jwtProvider;

    @Operation(summary = "회원가입")
    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponse signUp(@RequestBody SignupRequest request){ return userService.signup(request);}

    @Operation(summary = "로그인")
    @PostMapping("/login")
    @ResponseStatus(HttpStatus.CREATED)
    public TokenResponse login(@RequestBody LoginRequest request){
        UserResponse userResponse = userService.login(request);
        return jwtProvider.createTokenByLogin(userResponse);
    }

    @Operation(summary = "토큰 재발급")
    @GetMapping("/reissue")
    public TokenResponse reissue(
            @AuthenticationPrincipal AuthDetails authDetails
    ){
        UserResponse userResponse = UserResponse.of(authDetails.getUser());
        return jwtProvider.reissueAtk(userResponse);
    }

    @Operation(summary = "비밀번호 찾기-이메일 입력")
    @PostMapping("/lost/password")
    public void mail(@Valid @RequestBody MailRequest dto)throws Exception{
        userService.lostPassword(dto);
    }

    @Operation(summary = "비밀번호 찾기")
    @PatchMapping("/lost/password")
    public void setPassword(@Valid @RequestBody PasswordRequest request){
        userService.setPassword(request);
    }
}
