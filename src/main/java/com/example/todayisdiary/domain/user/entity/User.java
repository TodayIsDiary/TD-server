package com.example.todayisdiary.domain.user.entity;

import com.example.todayisdiary.domain.user.enums.Role;
import com.example.todayisdiary.domain.user.enums.Sex;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Email;

@NoArgsConstructor
@Entity
@Getter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 아이디
    private String accountId;

    // 닉네임
    @Length(min = 3, max = 12)
    private String nickName;

    @Email
    @Length(min = 6, max = 30)
    private String email;

    @Length(max = 68)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Sex sex;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    private String introduction;

    private String code;

    @Builder
    public User(String accountId, String nickName, String email, String password, String introduction, Sex sex){
        this.accountId = accountId;
        this.nickName = nickName;
        this.email = email;
        this.password = password;
        this.introduction = introduction;
        this.sex = sex;
        this.role = Role.USER;
    }

    public void setUser(String nickName, String introduction){
        if (nickName != null) this.nickName = nickName;
        if (introduction != null)this.introduction = introduction;
    }

    public void setPassword(String password){
        this.password = password;
    }

    public void setCode(String code){ this.code = code;}
}
