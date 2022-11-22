package com.example.todayisdiary.domain.Chat.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class ChatResponseList {
    private final List<ChatList> list;
}
