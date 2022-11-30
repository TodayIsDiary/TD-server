package com.example.todayisdiary.domain.report.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CommentReportList {
    private Long reportId;
    private String reporter;
    private String title;
}
