package com.example.todayisdiary.domain.chat.facade;

import com.example.todayisdiary.domain.chat.entity.Chat;
import com.example.todayisdiary.domain.chat.repository.ChatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ChatFacade {
    private final ChatRepository chatRepository;

    public Chat getChatById(Long id) {
        return chatRepository.findChatById(id)
                .orElseThrow(() -> new IllegalArgumentException("채팅을 찾지 못하였습니다."));
    }

    public List<Chat> getChatByIdList(Long id) {
        return chatRepository.findAllById(id);
    }

    public List<Chat> getChatAllById(Sort sort) {
        return chatRepository.findAll(sort);
    }

}
