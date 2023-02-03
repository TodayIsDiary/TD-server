package com.example.todayisdiary.domain.report.facade;

import com.example.todayisdiary.domain.report.entity.CommentReport;
import com.example.todayisdiary.domain.report.entity.Report;
import com.example.todayisdiary.domain.report.entity.UserReport;
import com.example.todayisdiary.domain.report.repository.CommentReportRepository;
import com.example.todayisdiary.domain.report.repository.ReportRepository;
import com.example.todayisdiary.domain.report.repository.UserReportRepository;
import com.example.todayisdiary.global.error.ErrorCode;
import com.example.todayisdiary.global.error.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ReportFacade {
    private final ReportRepository reportRepository;
    private final CommentReportRepository commentReportRepository;
    private final UserReportRepository userReportRepository;

    public Report getReportById(Long id) {
        return reportRepository.findReportById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
    }

    public CommentReport getCommentReportById(Long id) {
        return commentReportRepository.findCommentReportById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.REPORT_COMMENT_NOT_FOUND));
    }

    public UserReport getUserReportById(Long id){
        return userReportRepository.findUserReportById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.REPORT_BOARD_NOT_FOUND));

    }

    public List<Report> getReportByTitle(String title){
        return reportRepository.findAllReportByTitleSearch(title);
    }

    public List<CommentReport> getCommentReportByTitle(String title){
        return commentReportRepository.findAllCommentReportByTitleSearch(title);
    }

    public List<UserReport> getUserReportByTitle(String title){
        return userReportRepository.findUserReportsByTitleSearch(title);
    }

    public List<CommentReport> getCommentAllById(Sort sort){
        return commentReportRepository.findAll(sort);
    }

    public List<Report> getBoardAllById(Sort sort){
        return reportRepository.findAll(sort);
    }

    public List<UserReport> getUserAllById(Sort sort){return userReportRepository.findAll(sort);}
}
