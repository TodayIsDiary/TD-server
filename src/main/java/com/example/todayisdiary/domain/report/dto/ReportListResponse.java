package com.example.todayisdiary.domain.report.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class ReportListResponse {
    private final List<ReportList> list;
}
