package com.example.todayisdiary.domain.board.entity;

import com.example.todayisdiary.domain.board.enums.BoardCategory;
import com.example.todayisdiary.domain.user.entity.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "board")
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String content;

    private BoardCategory category;

    // 업데이트와 만들어진 시간
    private LocalDateTime boardTime;

    @PrePersist
    public void prePersist(){
        this.boardTime = LocalDateTime.now();
    }

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Builder
    public Board(String title, String content, BoardCategory category,User user){
        this.title = title;
        this.content = content;
        this.category = category;
        this.user = user;
    }

    public void setBord(String title, String content, BoardCategory category){
        this.title = title;
        this.content = content;
        this.category = category;
    }
}
