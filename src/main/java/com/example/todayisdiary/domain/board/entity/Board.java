package com.example.todayisdiary.domain.board.entity;

import com.example.todayisdiary.domain.board.enums.ContentCategory;
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
