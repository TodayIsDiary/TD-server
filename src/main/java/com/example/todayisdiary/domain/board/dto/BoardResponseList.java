package com.example.todayisdiary.domain.board.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class BoardResponseList {
    private final List<BoardList> list;
}
