package com.example.todayisdiary.domain.comment.service;

import com.example.todayisdiary.domain.comment.dto.CommentRequest;
import com.example.todayisdiary.domain.comment.dto.CommentResponseList;

public interface CommentService {
    void createChat(CommentRequest request, Long id);

    void createReplyChat(CommentRequest request, Long id);

    void setChat(CommentRequest request, Long id);

    void deleteChat(Long id);

    CommentResponseList chatList(Long id);

    CommentResponseList chatReplyList(Long id);
}
