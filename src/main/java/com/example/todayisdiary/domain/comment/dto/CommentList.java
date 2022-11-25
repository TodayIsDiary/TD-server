package com.example.todayisdiary.domain.comment.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CommentList {
    private final Long id;
    private final String comment;
    private final String writer;
    private final String date;
    private final Long originChatId;
    private final Long replyChatId;
}
