package com.example.todayisdiary.domain.report.service;

import com.example.todayisdiary.domain.report.dto.*;

public interface ReportService {
    void createReport(ReportRequest request, Long id);

    void createCommentReport(ReportRequest request, Long id);

    void delReport(Long id);

    void delCommentReport(Long id);

    ResponseBoardList reportBoardList();

    ResponseCommentList reportCommentList();

    ReportResponse detailReport(Long id);

    CommentReportResponse detailCommentReport(Long id);

    void forceDelBoard(Long id);

    void forceDelComment(Long id);
}
