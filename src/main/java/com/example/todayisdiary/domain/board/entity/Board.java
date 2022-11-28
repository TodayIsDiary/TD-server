package com.example.todayisdiary.domain.board.entity;

import com.example.todayisdiary.domain.comment.entity.Comment;
import com.example.todayisdiary.domain.board.enums.BoardCategory;
import com.example.todayisdiary.domain.like.entity.BoardLove;
import com.example.todayisdiary.domain.report.entity.Report;
import com.example.todayisdiary.domain.user.entity.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String content;

    private BoardCategory category;

    // 업데이트와 만들어진 시간
    private LocalDateTime boardTime;

    @ColumnDefault("false")
    private boolean isLiked;

    private int heart;

    @PrePersist
    public void prePersist(){
        this.boardTime = LocalDateTime.now();
    }

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "board", cascade = CascadeType.REMOVE)
    private List<BoardLove> boardLoves;

    @OneToMany(mappedBy = "board", cascade = CascadeType.REMOVE)
    private List<Report> reports;

    @OneToMany(mappedBy = "board", cascade = CascadeType.REMOVE)
    private List<Comment> comments;

    public void likes(){
        this.isLiked = true;
    }

    public void unlikes(){
        this.isLiked = false;
    }

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

    public void addHeart(){
        this.heart += 1;
    }

    public void deleteHeart(){
        this.heart -= 1;
    }
}
