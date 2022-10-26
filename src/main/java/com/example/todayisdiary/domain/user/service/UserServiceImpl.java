package com.example.todayisdiary.domain.user.service;

import com.example.todayisdiary.domain.user.dto.*;
import com.example.todayisdiary.domain.user.entity.User;
import com.example.todayisdiary.domain.user.repository.UserRepository;
import com.example.todayisdiary.global.mail.dto.MailRequest;
import com.example.todayisdiary.global.mail.service.MailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final MailService mailService;

    @Override
    public void signup(SignupRequest request) {
        boolean exists = userRepository.existsByEmail(request.getEmail());
        boolean exists2 = userRepository.existsByAccountId(request.getAccountId());
        if (exists) throw new IllegalStateException("이미 가입하신 이메일 입니다.");
        if (exists2) throw new IllegalStateException("이미 있는 아이디 입니다.");

        User user = User.builder()
                .accountId(request.getAccountId())
                .nickName(request.getNickName())
                .email(request.getEmail())
                .sex(request.getSex())
                .password(passwordEncoder.encode(request.getPassword()))
                .introduction(request.getIntroduction()).build();

        userRepository.save(user);
    }

    @Transactional
    @Override
    public UserResponse login(LoginRequest request) {

        User user = userRepository.findByAccountId(request.getAccountId())
                .orElseThrow(() -> new IllegalArgumentException("아이디가 맞지 않습니다."));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new IllegalStateException("비밀번호가 맞지 않습니다.");
        }

        return UserResponse.of(user);
    }

    @Override
    public void lostPassword(MailRequest mailDto) throws Exception {

        User user = userRepository.findByEmail(mailDto.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("이메일을 찾을 수 없습니다."));

        mailService.mailSend(mailDto, user.getAccountId());

    }

    @Transactional
    @Override
    public void setPassword(PasswordRequest request) {

        User user = userRepository.findByCode(request.getCode())
                .orElseThrow(() -> new IllegalArgumentException("코드를 다시 입력 해주세요.."));

        if (user.getCode() != null) {

            if (!request.getNewPassword().equals(request.getNewPasswordValid())) {
                throw new IllegalStateException("비밀번호가 맞지 않습니다.");
            }

            user.setPassword(passwordEncoder.encode(request.getNewPassword()));
            user.setCode(null);
            userRepository.save(user);
        }

    }

    @Override
    public void setPasswords(String accountId, PasswordRequest request) {

        User user = userRepository.findByAccountId(accountId)
                .orElseThrow(() -> new IllegalArgumentException("회원을 찾을 수 없습니다."));

        if (passwordEncoder.matches(request.getOriginalPassword(), user.getPassword())) {
            if (request.getNewPassword().equals(request.getNewPasswordValid())) {
                user.setPassword(passwordEncoder.encode(request.getNewPassword()));
                userRepository.save(user);
                log.info("{} 님의 비밀번호가 {} 바꼈습니다.", accountId, request.getNewPassword());
            } else throw new IllegalStateException("변경하는 비밀번호가 맞지 않습니다.");
        } else throw new IllegalStateException("비밀번호가 맞지 않습니다.");
    }

    @Override
    public User getUser(String accountId) {

        return userRepository.findByAccountId(accountId)
                .orElseThrow(() -> new IllegalArgumentException("회원을 찾을 수 없습니다."));
    }

    @Override
    public void setUser(String accountId, UserRequest request) {

        User user = userRepository.findByAccountId(accountId)
                .orElseThrow(() -> new IllegalArgumentException("회원을 찾을 수 없습니다."));

        user.setUser(request.getNickName(), request.getIntroduction());
        userRepository.save(user);
    }

    @Override
    public void leaveUser(String accountId) {

        User user = userRepository.findByAccountId(accountId)
                .orElseThrow(() -> new IllegalArgumentException("회원을 찾을 수 없습니다."));

        userRepository.delete(user);
    }

    // 자신의 작성한 게시글 리스트, GET, /user/title-list
    public void myPost() {

    }


}
