package com.example.todayisdiary.domain.Chat.entity;

import com.example.todayisdiary.domain.board.entity.Board;
import com.example.todayisdiary.domain.user.entity.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
public class Chat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String writer;

    private String comment;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "board_id", nullable = false)
    private Board board;

    @Builder
    public Chat(String writer, String comment, User user, Board board) {
        this.writer = writer;
        this.comment = comment;
        this.user = user;
        this.board = board;
    }

}
