package com.example.todayisdiary.domain.Chat.entity;

import com.example.todayisdiary.domain.board.entity.Board;
import com.example.todayisdiary.domain.user.entity.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Chat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String writer;

    private String comment;

    private Long originChatId;

    private Long replyChatId;

    // 업데이트와 만들어진 시간
    private LocalDateTime chatTime;

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

    public void isOrigin(){
        this.originChat = true;
    }

    @Builder
    public Chat(String writer, String comment, User user, Board board, Long originChatId, Long replyChatId) {
        this.writer = writer;
        this.comment = comment;
        this.user = user;
        this.board = board;
        this.originChatId = originChatId;
        this.replyChatId= replyChatId;
    }

    public void setChat(String comment){
        this.comment = comment;
    }

}
