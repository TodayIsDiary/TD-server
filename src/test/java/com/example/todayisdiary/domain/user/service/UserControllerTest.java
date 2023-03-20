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
import org.springframework.test.annotation.Rollback;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class UserControllerTest {
    @Autowired
    UserRepository userRepository;

    @Autowired
    UserFacade userFacade;

    @Test
    @DisplayName("회원가입 전 체크")
    @Rollback(value = true)
    void checkSignup() {
        User user = User.builder()
                .email("ntaekeulgom@gmail.com")
                .accountId("beargamess")
                .password("hoyoung8291!")
                .nickName("곰겜")
                .providerType(ProviderType.LOCAL)
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
}