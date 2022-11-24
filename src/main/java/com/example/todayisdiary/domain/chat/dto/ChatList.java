package com.example.todayisdiary.domain.chat.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ChatList {
    private final Long id;
    private final String comment;
    private final String writer;
    private final String date;
    private final Long originChatId;
    private final Long replyChatId;
}
