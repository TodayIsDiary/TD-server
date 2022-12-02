package com.example.todayisdiary.domain.report.entity;

import com.example.todayisdiary.domain.comment.entity.Comment;
import com.example.todayisdiary.domain.user.entity.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommentReport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comment_id")
    private Comment comment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private String title;

    private String content;

    @Builder
    public CommentReport(String title, String content, User user, Comment comment){
        this.title = title;
        this.content = content;
        this.user = user;
        this.comment = comment;
    }
}
