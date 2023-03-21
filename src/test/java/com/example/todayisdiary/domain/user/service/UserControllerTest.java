package com.example.todayisdiary.domain.user.service;

import com.example.todayisdiary.domain.user.entity.User;
import com.example.todayisdiary.domain.user.enums.Sex;
import com.example.todayisdiary.domain.user.facade.UserFacade;
import com.example.todayisdiary.domain.user.repository.UserRepository;
import com.example.todayisdiary.global.security.oauth.entity.ProviderType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.Rollback;

import javax.transaction.Transactional;

import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class UserControllerTest {
    @Autowired
    UserRepository userRepository;

    @Autowired
    UserFacade userFacade;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Test
    @Transactional
    @DisplayName("회원가입 체크")
    @Rollback(value = true)
    void checkSignup() {
        User user = User.builder()
                .email("ntaekeulgom@gmail.com")
                .accountId("beargamess")
                .password(passwordEncoder.encode("abcdegh8291!"))
                .nickName("홍길동")
                .providerType(ProviderType.LOCAL)
                .imageUrl(defaultImage(null))
                .introduction("안녕하세요.")
                .sex(Sex.MALE)
                .build();

        User join = userRepository.save(user);
        User u = userFacade.getUserById(join.getId());
        assertThat(u.getId()).isEqualTo(join.getId());
        assertThat(u).isEqualTo(join);
    }

    @Test
    void signup() {
    }

    @Test
    void login() {
    }

    @Test
    void newNickName() {
    }

    @Test
    void lostPassword() {
    }

    @Test
    void setPasswordCodeCheck() {
    }

    @Test
    void setPasswordEmail() {
    }

    @Test
    void setPasswords() {
    }

    @Test
    void myUser() {
    }

    @Test
    void getUser() {
    }

    @Test
    void setUser() {
    }

    @Test
    void leaveUser() {
    }

    @Test
    void signupEmail() {
    }

    private String defaultImage(String imageUrl){
        if(Objects.equals(imageUrl, "null")){return "4f743a16-e96f-49e7-9c11-0948592dab18-5087579.png";}
        else return imageUrl == null ? "4f743a16-e96f-49e7-9c11-0948592dab18-5087579.png" : imageUrl;
    }
}