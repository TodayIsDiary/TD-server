package com.example.todayisdiary.domain.report.facade;

import com.example.todayisdiary.domain.report.entity.CommentReport;
import com.example.todayisdiary.domain.report.entity.Report;
import com.example.todayisdiary.domain.report.repository.CommentReportRepository;
import com.example.todayisdiary.domain.report.repository.ReportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ReportFacade {
    private final ReportRepository reportRepository;
    private final CommentReportRepository commentReportRepository;

    public Report getReportById(Long id) {
        return reportRepository.findReportById(id)
                .orElseThrow(() -> new IllegalArgumentException("신고내용을 찾을 수 없습니다."));
    }

    public CommentReport getCommentReportById(Long id) {
        return commentReportRepository.findCommentReportById(id)
                .orElseThrow(() -> new IllegalArgumentException("신고내용을 찾을 수 없습니다."));
    }

    public List<CommentReport> getCommentAllById(Sort sort){
        return commentReportRepository.findAll(sort);
    }

    public List<Report> getBoardAllById(Sort sort){
        return reportRepository.findAll(sort);
    }
}
