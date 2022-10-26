package com.example.todayisdiary.domain.user.service;

import com.example.todayisdiary.domain.user.dto.*;
import com.example.todayisdiary.domain.user.entity.User;
import com.example.todayisdiary.global.mail.dto.MailRequest;

public interface UserService {

    // 회원 가입 POST, /user/signup
    void signup(SignupRequest request);

    // 자체 로그인, POST , /user/login
    UserResponse login(LoginRequest request);

    // 이메일 코드 보낼때, POST , /user/lost/password
    void lostPassword(MailRequest mailDto) throws Exception;

    // 코드 인증후 비밀번호 변경, PATCH , /user/lost/password
    void setPassword(PasswordRequest request);

    // 토큰 비밀번호 변경, PATCH, /user/password
    void setPasswords(String accountId, PasswordRequest request);

    // 내 정보 불러오기, GET, /user
    User getUser(String accountId);

    // 마이페이지 수정, PATCH, /user/change , 수정 되는것 : nickName, introduction
    void setUser(String accountId, UserRequest request);

    // 회원 탈퇴하기, DELETE, /user/leave
    void leaveUser(String accountId);
}
