package com.example.todayisdiary.domain.report.service;

import com.example.todayisdiary.domain.board.entity.Board;
import com.example.todayisdiary.domain.board.facade.BoardFacade;
import com.example.todayisdiary.domain.board.repository.BoardRepository;
import com.example.todayisdiary.domain.comment.entity.Comment;
import com.example.todayisdiary.domain.comment.facade.CommentFacade;
import com.example.todayisdiary.domain.comment.repository.CommentRepository;
import com.example.todayisdiary.domain.report.dto.*;
import com.example.todayisdiary.domain.report.entity.CommentReport;
import com.example.todayisdiary.domain.report.entity.Report;
import com.example.todayisdiary.domain.report.facade.ReportFacade;
import com.example.todayisdiary.domain.report.repository.CommentReportRepository;
import com.example.todayisdiary.domain.report.repository.ReportRepository;
import com.example.todayisdiary.domain.user.entity.User;
import com.example.todayisdiary.domain.user.enums.Role;
import com.example.todayisdiary.domain.user.facade.UserFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService {
    private final UserFacade userFacade;
    private final BoardFacade boardFacade;
    private final ReportFacade reportFacade;
    private final CommentFacade commentFacade;
    private final ReportRepository reportRepository;
    private final CommentRepository commentRepository;
    private final CommentReportRepository commentReportRepository;
    private final BoardRepository boardRepository;

    @Override
    @Transactional
    public void createReport(ReportRequest request, Long id) {
        User user = userFacade.getCurrentUser();
        Board board = boardFacade.getBoardById(id);

        Report report = new Report(
                request.getTitle(),
                request.getContent(),
                user,
                board
        );
        reportRepository.save(report);
    }

    @Override
    @Transactional
    public void createCommentReport(ReportRequest request, Long id) {
        User user = userFacade.getCurrentUser();
        Comment comment = commentFacade.getChatById(id);

        CommentReport report = new CommentReport(
                request.getTitle(),
                request.getContent(),
                user,
                comment
        );
        commentReportRepository.save(report);
    }

    @Override
    @Transactional
    public void delReport(Long id) {
        adminAccount();
        Report report = reportFacade.getReportById(id);
        reportRepository.delete(report);
    }

    @Override
    @Transactional
    public void delCommentReport(Long id) {
        adminAccount();
        CommentReport commentReport = reportFacade.getCommentReportById(id);
        commentReportRepository.delete(commentReport);
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseBoardList reportBoardList() {
        adminAccount();
        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        List<Report> reports = reportFacade.getBoardAllById(sort);
        List<ReportList> reportLists = new ArrayList<>();

        for (Report report : reports) {
            ReportList dto = ReportList.builder()
                    .reportId(report.getId())
                    .title(report.getTitle())
                    .reporter(report.getUser().getNickName()).build();
            reportLists.add(dto);
        }
        return new ResponseBoardList(reportLists);
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseCommentList reportCommentList() {
        adminAccount();
        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        List<CommentReport> reports = reportFacade.getCommentAllById(sort);
        List<CommentReportList> reportLists = new ArrayList<>();

        for (CommentReport report : reports) {
            CommentReportList dto = CommentReportList.builder()
                    .reportId(report.getId())
                    .title(report.getTitle())
                    .reporter(report.getUser().getNickName()).build();
            reportLists.add(dto);
        }
        return new ResponseCommentList(reportLists);
    }

    @Override
    @Transactional(readOnly = true)
    public ReportResponse detailReport(Long id) {
        adminAccount();
        Report report = reportFacade.getReportById(id);

        return ReportResponse.builder()
                .reportId(report.getId())
                .reporter(report.getUser().getNickName())
                .title(report.getTitle())
                .content(report.getContent())
                .boardTitle(report.getBoard().getTitle())
                .boardContent(report.getBoard().getContent())
                .boardId(report.getBoard().getId()).build();
    }

    @Override
    @Transactional(readOnly = true)
    public CommentReportResponse detailCommentReport(Long id) {
        adminAccount();
        CommentReport report = reportFacade.getCommentReportById(id);

        return CommentReportResponse.builder()
                .reportId(report.getId())
                .reporter(report.getUser().getNickName())
                .title(report.getTitle())
                .content(report.getContent())
                .comment(report.getComment().getComments())
                .commentId(report.getComment().getId()).build();
    }

    @Override
    @Transactional
    public void forceDelBoard(Long id) {
        adminAccount();
        Board board = boardFacade.getBoardById(id);
        boardRepository.delete(board);
    }

    @Override
    @Transactional
    public void forceDelComment(Long id) {
        adminAccount();
        Comment comment = commentFacade.getChatById(id);
        commentRepository.delete(comment);
    }

    private void adminAccount() {
        if (userFacade.getCurrentUser().getRole() != Role.ADMIN) throw new IllegalStateException("어드민 계정이 아닙니다.");
    }

}
