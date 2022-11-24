package com.example.todayisdiary.domain.chat.service;

import com.example.todayisdiary.domain.chat.dto.ChatRequest;
import com.example.todayisdiary.domain.chat.dto.ChatResponseList;

public interface ChatService {
    void createChat(ChatRequest request, Long id);

    void createReplyChat(ChatRequest request, Long id);

    void setChat(ChatRequest request, Long id);

    void deleteChat(Long id);

    ChatResponseList chatList(Long id);

    ChatResponseList chatReplyList(Long id);
}
