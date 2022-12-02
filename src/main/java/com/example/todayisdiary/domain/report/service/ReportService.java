package com.example.todayisdiary.domain.report.service;

import com.example.todayisdiary.domain.report.dto.*;
import com.example.todayisdiary.domain.report.dto.request.ReportRequest;

public interface ReportService {
    void createReport(ReportRequest request, Long id);

    void createCommentReport(ReportRequest request, Long id);

    void createUserReport(ReportRequest request, Long id);

    void delReport(Long id);

    void delCommentReport(Long id);

    void delUserReport(Long id);

    ReportListResponse reportBoardList();

    ReportListResponse userReportList();

    ReportListResponse reportBoardFilterList(String title);

    ReportListResponse reportCommentList();

    ReportListResponse reportCommentFilterList(String title);

    ReportListResponse userReportFilterList(String title);

    ReportResponse detailReport(Long id);

    CommentReportResponse detailCommentReport(Long id);

    UserReportResponse detailUserReport(Long id);

    void forceDelBoard(Long id);

    void forceDelComment(Long id);

    void forceDelUser(Long id);
}
