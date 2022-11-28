package com.example.todayisdiary.domain.like.service;

public interface CommentLikeService {
    void commentLike(Long commentId);

    void commentLikeCancel(Long commentId);
}
