package com.example.todayisdiary.domain.user.controller;

import com.example.todayisdiary.domain.user.dto.*;
import com.example.todayisdiary.domain.user.entity.User;
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
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final JwtProvider jwtProvider;

    @Operation(summary = "회원가입")
    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.CREATED)
    public void signUp(@RequestBody SignupRequest request){ userService.signup(request);}

    @Operation(summary = "로그인")
    @PostMapping("/login")
    @ResponseStatus(HttpStatus.CREATED)
    public TokenResponse login(@RequestBody LoginRequest request){
        UserResponse userResponse = userService.login(request);
        return jwtProvider.createTokenByLogin(userResponse);
    }

    @Operation(summary = "토큰 재발급")
    @PostMapping("/reissue")
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

    @Operation(summary = "이메일-비밀번호 찾기")
    @PatchMapping("/lost/password")
    public void setEmailPassword(@Valid @RequestBody PasswordRequest request){
        userService.setPasswordEmail(request);
    }

    @Operation(summary = "비밀번호 변경-토큰")
    @PatchMapping("/password")
    public void setPassword( @Valid @RequestBody PasswordRequest request)
    {
        userService.setPasswords(request);
    }

    @Operation(summary = "내 정보 불러오기")
    @GetMapping()
    public UserInfoResponse getUser(){ return userService.getUser();}

    @Operation(summary = "내 정보 수정하기")
    @PatchMapping("/change")
    public void setUser(@RequestBody UserRequest request)
    {
        userService.setUser(request);
    }

    @Operation(summary = "회원 탈퇴하기")
    @DeleteMapping("/leave")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void leaveUser(){ userService.leaveUser();}

    @Operation(summary = "회원가입 이메일 인증")
    @PostMapping("/email")
    @ResponseStatus(HttpStatus.CREATED)
    public void signupEmail(@RequestBody MailRequest request) throws Exception {
        userService.signupEmail(request);
    }

}
