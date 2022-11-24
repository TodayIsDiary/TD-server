package com.example.todayisdiary.domain.board.dto;

import com.example.todayisdiary.domain.board.enums.BoardCategory;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class BoardList {
    private final Long boardId;
    private final String title;
    private final BoardCategory category;
    private final String date; // 몇분전 글인지 볼 수 있게 해준다. 예 : 방금전, 5분전

}
