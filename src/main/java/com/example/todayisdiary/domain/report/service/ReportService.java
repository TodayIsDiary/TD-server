package com.example.todayisdiary.domain.report.service;

import com.example.todayisdiary.domain.report.dto.ReportList;
import com.example.todayisdiary.domain.report.dto.ReportRequest;
import com.example.todayisdiary.domain.report.dto.ReportResponse;

import java.util.List;

public interface ReportService {
    void createReport(ReportRequest request, Long id);

    void delReport(Long id);

    List<ReportList> reportBoardList();

    ReportResponse detailReport(Long id);

    void forceDelBoard(Long id);
}
