package com.example.todayisdiary.domain.comment.facade;

import com.example.todayisdiary.domain.comment.entity.Chat;
import com.example.todayisdiary.domain.comment.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CommentFacade {
    private final CommentRepository commentRepository;

    public Chat getChatById(Long id) {
        return commentRepository.findChatById(id)
                .orElseThrow(() -> new IllegalArgumentException("채팅을 찾지 못하였습니다."));
    }

    public List<Chat> getChatByIdList(Long id) {
        return commentRepository.findAllById(id);
    }

    public List<Chat> getChatAllById(Sort sort) {
        return commentRepository.findAll(sort);
    }

}
