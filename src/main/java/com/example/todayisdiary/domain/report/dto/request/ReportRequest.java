package com.example.todayisdiary.domain.report.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ReportRequest {
    private String title;
    private String content;
}
