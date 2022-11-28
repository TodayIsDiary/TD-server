package com.example.todayisdiary.domain.comment.entity;

import com.example.todayisdiary.domain.board.entity.Board;
import com.example.todayisdiary.domain.like.entity.CommentLove;
import com.example.todayisdiary.domain.user.entity.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String writer;

    private String comments;

    private Long originChatId;

    private Long replyChatId;

    // 업데이트와 만들어진 시간
    private LocalDateTime chatTime;

    private int heart;

    @ColumnDefault("false")
    private boolean originChat;

    @PrePersist
    public void prePersist(){
        this.chatTime = LocalDateTime.now();
    }

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "board_id", nullable = false)
    private Board board;

    @OneToMany(mappedBy = "comment", cascade = CascadeType.REMOVE)
    private List<CommentLove> commentLoves;

    public void isOrigin(){
        this.originChat = true;
    }

    @Builder
    public Comment(String writer, String comments, User user, Board board, Long originChatId, Long replyChatId) {
        this.writer = writer;
        this.comments = comments;
        this.user = user;
        this.board = board;
        this.originChatId = originChatId;
        this.replyChatId= replyChatId;
    }

    public void setChat(String comment){
        this.comments = comment;
    }

    public void addHeart(){
        this.heart += 1;
    }

    public void deleteHeart(){
        this.heart -= 1;
    }

}
