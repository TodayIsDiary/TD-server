package com.example.todayisdiary.domain.board.dto;

import com.example.todayisdiary.domain.board.enums.BoardCategory;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class BoardResponse {
    private Long boardId;

    private String title;

    private String content;

    private BoardCategory category;

    private LocalDateTime boardTime;

    private String writer;

    private int heart;

    private boolean isLiked;

}
