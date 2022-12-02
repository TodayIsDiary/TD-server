package com.example.todayisdiary.domain.report.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserReportResponse {
    private Long reportId;
    private String reporter;
    private String title;
    private String content;
    private String introduce;
    private Long userId;
    private String accountId;
    private String nickName;
}
