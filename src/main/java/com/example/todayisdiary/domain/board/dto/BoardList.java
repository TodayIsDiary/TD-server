package com.example.todayisdiary.domain.board.dto;

import com.example.todayisdiary.domain.board.enums.BoardCategory;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class BoardList {
    private Long boardId;
    private String title;
    private BoardCategory category;
    private String date; // 몇분전 글인지 볼 수 있게 해준다. 예 : 방금전, 5분전

}
