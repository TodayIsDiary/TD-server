package com.example.todayisdiary.domain.board.dto;

import com.example.todayisdiary.domain.board.enums.BoardCategory;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BoardRequest {
    private String title;
    private String content;
    private BoardCategory category;
    private String imageUrl;
}
