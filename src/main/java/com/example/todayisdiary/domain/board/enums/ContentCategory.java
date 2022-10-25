package com.example.todayisdiary.domain.board.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ContentCategory {

    HAPPY("HAPPY", "기쁨"),
    SAD("SAD", "슬픔"),
    ANGRY("ANGRY", "화남"),
    SURPRISE("SURPRISE", "놀람");

    private final String mood;

    private final String title;
}
