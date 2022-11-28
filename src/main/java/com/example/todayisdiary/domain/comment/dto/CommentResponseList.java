package com.example.todayisdiary.domain.comment.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class CommentResponseList {
    private final List<CommentList> list;
}
