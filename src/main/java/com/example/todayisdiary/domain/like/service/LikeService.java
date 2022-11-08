package com.example.todayisdiary.domain.like.service;

public interface LikeService {
    void boardLike(Long diaryId);

    void boardLikeCancel(Long diaryId);
}
