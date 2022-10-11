package com.example.todayisdiary.domain.user.service;

import com.example.todayisdiary.domain.user.dto.LoginRequest;
import com.example.todayisdiary.domain.user.dto.PasswordRequest;
import com.example.todayisdiary.domain.user.dto.SignupRequest;
import com.example.todayisdiary.domain.user.dto.UserResponse;
import com.example.todayisdiary.domain.user.entity.User;
import com.example.todayisdiary.domain.user.repository.UserRepository;
import com.example.todayisdiary.global.mail.dto.MailDto;
import com.example.todayisdiary.global.mail.entity.Mail;
import com.example.todayisdiary.global.mail.repository.MailRepository;
import com.example.todayisdiary.global.mail.service.MailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final MailRepository mailRepository;
    private final MailService mailService;

    public UserResponse signup(SignupRequest request){
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

        user = userRepository.save(user);
        log.info("user = {}", user);
        return UserResponse.of(user);
    }

    @Transactional
    public UserResponse login(LoginRequest request){

        User user = userRepository.findByAccountId(request.getAccountId())
                .orElseThrow(() -> new IllegalArgumentException("아이디가 맞지 않습니다."));

        if(passwordEncoder.matches(request.getPassword(), user.getPassword())){
            return UserResponse.of(user);
        }else throw new IllegalStateException("비밀번호가 맞지 않습니다.");
    }

    // 이메일 코드 보낼때
    public void lostPassword(MailDto mailDto){

        User user = userRepository.findByEmail(mailDto.getAdress())
                .orElseThrow(() -> new IllegalArgumentException("이메일을 찾을 수 없습니다."));

        mailService.mailSend(mailDto,user.getAccountId());

    }

    // 코드 인증후 비밀번호 변경
    public void setPassword(PasswordRequest request){

        Mail mail = mailRepository.findByCode(request.getCode())
                .orElseThrow(() -> new IllegalArgumentException("코드를 다시 입력 해주세요."));

        User user = userRepository.findByAccountId(mail.getAccountId())
                .orElseThrow(() -> new IllegalArgumentException("인증번호와 맞는 이메일이 없습니다..?"));

        if(request.getNewPassword().equals(request.getNewPasswordValid())){
            user.setPassword(passwordEncoder.encode(request.getNewPassword()));
            userRepository.save(user);
            mailRepository.delete(mail);
        }else throw new IllegalStateException("비밀번호가 맞지 않습니다.");
    }

    // 토큰 비밀번호 변경
    public void setPasswords(String accountId, PasswordRequest request){

    }
}
