package com.example.todayisdiary.domain.report.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ReportList {
    private Long reportId;
    private String reporter;
    private String title;
    private Long boardId;
}
