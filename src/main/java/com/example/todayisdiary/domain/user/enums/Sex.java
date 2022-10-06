package com.example.todayisdiary.domain.user.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Sex {
    MALE("male", "남성"),
    FEMALE("female", "여성");

    private final String key;
    private final String title;
}
