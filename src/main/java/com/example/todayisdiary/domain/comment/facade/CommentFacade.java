package com.example.todayisdiary.domain.comment.facade;

import com.example.todayisdiary.domain.comment.entity.Comment;
import com.example.todayisdiary.domain.comment.repository.CommentRepository;
import com.example.todayisdiary.global.error.ErrorCode;
import com.example.todayisdiary.global.error.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CommentFacade {
    private final CommentRepository commentRepository;

    public Comment getChatById(Long id) {
        return commentRepository.findChatById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.COMMENT_NOT_FOUND));
    }

    public List<Comment> getChatByOriginId(Long id) {
        return commentRepository.findCommentByOriginChatId(id);
    }

    public List<Comment> getChatAllById(Sort sort) {
        return commentRepository.findAll(sort);
    }

}
