package com.example.todayisdiary.domain.user.entity;

import com.example.todayisdiary.domain.comment.entity.Comment;
import com.example.todayisdiary.domain.board.entity.Board;
import com.example.todayisdiary.domain.like.entity.BoardLove;
import com.example.todayisdiary.domain.like.entity.CommentLove;
import com.example.todayisdiary.domain.report.entity.Report;
import com.example.todayisdiary.domain.report.entity.UserReport;
import com.example.todayisdiary.domain.user.enums.Role;
import com.example.todayisdiary.domain.user.enums.Sex;
import com.example.todayisdiary.global.mail.entity.Mail;
import com.example.todayisdiary.global.security.oauth.entity.ProviderType;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Getter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 아이디
    @Length(min = 1, max = 20)
    private String accountId;

    // 닉네임
    @Length(min = 2, max = 12)
    private String nickName;

    @Email
    @Length(min = 6, max = 30)
    private String email;

    private int visit;

    @Length(max = 68)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Sex sex;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Length(min = 1, max = 30)
    private String introduction;

    private String imageUrl;

    @Enumerated(EnumType.STRING)
    @NotNull
    private ProviderType providerType;

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    private List<Board> boards;

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    private List<Mail> mails;

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    private List<BoardLove> boardLoves;

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    private List<CommentLove> commentLoves;

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    private List<Report> reports;

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    private List<Comment> comments;

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    private List<UserReport> userReports;

    @Builder
    public User(String accountId, String nickName, String email, String password, String introduction, Sex sex, String imageUrl, ProviderType providerType){
        this.accountId = accountId;
        this.nickName = nickName;
        this.email = email;
        this.password = password;
        this.introduction = introduction;
        this.sex = sex;
        this.role = Role.ROLE_USER;
        this.imageUrl = imageUrl;
        this.providerType = providerType;
    }

    public void userProfileChange(String imageUrl){this.imageUrl = imageUrl;}

    public void addVisit(){
        this.visit += 1;
    }

    public void setUser(String nickName, String introduction){
        this.nickName = nickName;
        this.introduction = introduction;
    }

    public void setNickName(String nickName){
        this.nickName = nickName;
    }

    public void setPassword(String password){
        this.password = password;
    }

}
