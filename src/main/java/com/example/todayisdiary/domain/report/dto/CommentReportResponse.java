package com.example.todayisdiary.domain.report.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CommentReportResponse {
    private Long reportId;
    private String reporter;
    private String title;
    private String content;
    private String comment;
    private Long commentId;
    private String accountId;
}
