package com.example.todayisdiary.domain.report.facade;

import com.example.todayisdiary.domain.report.entity.CommentReport;
import com.example.todayisdiary.domain.report.entity.Report;
import com.example.todayisdiary.domain.report.entity.UserReport;
import com.example.todayisdiary.domain.report.repository.CommentReportRepository;
import com.example.todayisdiary.domain.report.repository.ReportRepository;
import com.example.todayisdiary.domain.report.repository.UserReportRepository;
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
                .orElseThrow(() -> new IllegalArgumentException("신고된 일기를 찾을 수 없습니다."));
    }

    public CommentReport getCommentReportById(Long id) {
        return commentReportRepository.findCommentReportById(id)
                .orElseThrow(() -> new IllegalArgumentException("신고된 댓글을 찾을 수 없습니다."));
    }

    public UserReport getUserReportById(Long id){
        return userReportRepository.findUserReportById(id)
                .orElseThrow(() -> new IllegalArgumentException("신고된 유저를 찾을 수 없습니다."));

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
