package com.example.todayisdiary.domain.report.dto;

import com.example.todayisdiary.domain.user.enums.Sex;
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
    private String nickName;
    private String imageUrl;
    private String email;
    private Sex sex;
}
