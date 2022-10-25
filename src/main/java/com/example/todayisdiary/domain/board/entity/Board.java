package com.example.todayisdiary.domain.board.entity;

import com.example.todayisdiary.domain.board.enums.ContentCategory;
import com.example.todayisdiary.domain.user.entity.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Entity
@Table(name = "board")
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String content;

    private ContentCategory category;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Builder
    public Board(String title, String content, ContentCategory category){
        this.title = title;
        this.content = content;
        this.category = category;
    }

    public void setBord(String title, String content, ContentCategory category){
        this.title = title;
        this.content = content;
        this.category = category;
    }
}
