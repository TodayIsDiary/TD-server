package com.example.todayisdiary.global.mail.entity;

import com.example.todayisdiary.domain.user.entity.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;

@Entity
@Getter
@NoArgsConstructor
public class Mail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String code;

    @Email
    private String email;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = true)
    private User user;

    @Builder
    public Mail(String code, String email){
        this.code = code;
        this.email = email;
    }

    @Builder
    public Mail(String code, String email, User user){
        this.code = code;
        this.email = email;
        this.user = user;
    }

}
