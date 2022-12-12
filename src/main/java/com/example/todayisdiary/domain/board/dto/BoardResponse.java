package com.example.todayisdiary.domain.board.dto;

import com.example.todayisdiary.domain.board.enums.BoardCategory;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class BoardResponse {
    private final Long boardId;

    private final String title;

    private final String content;

    private final BoardCategory category;

    private final String boardTime;

    private final String writer;

    private final int heart;

    private final boolean isLiked;

    private final int commentCount;

    private final Long userId;

    private final String imageUrl;
}
