package com.example.todayisdiary.domain.report.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ReportResponse {
    private Long reportId;
    private String reporter;
    private String title;
    private String content;
    private String boardTitle;
    private String boardContent;
    private Long boardId;
    private String accountId;
}
