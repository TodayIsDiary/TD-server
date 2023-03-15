package com.example.todayisdiary.domain.report.service;

import com.example.todayisdiary.domain.board.entity.Board;
import com.example.todayisdiary.domain.board.facade.BoardFacade;
import com.example.todayisdiary.domain.board.repository.BoardRepository;
import com.example.todayisdiary.domain.comment.entity.Comment;
import com.example.todayisdiary.domain.comment.facade.CommentFacade;
import com.example.todayisdiary.domain.comment.repository.CommentRepository;
import com.example.todayisdiary.domain.report.dto.*;
import com.example.todayisdiary.domain.report.dto.request.ReportRequest;
import com.example.todayisdiary.domain.report.entity.CommentReport;
import com.example.todayisdiary.domain.report.entity.Report;
import com.example.todayisdiary.domain.report.entity.UserReport;
import com.example.todayisdiary.domain.report.facade.ReportFacade;
import com.example.todayisdiary.domain.report.repository.CommentReportRepository;
import com.example.todayisdiary.domain.report.repository.ReportRepository;
import com.example.todayisdiary.domain.report.repository.UserReportRepository;
import com.example.todayisdiary.domain.user.entity.User;
import com.example.todayisdiary.domain.user.facade.UserFacade;
import com.example.todayisdiary.domain.user.repository.UserRepository;
import com.example.todayisdiary.global.s3.facade.S3Facade;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService {
    private final S3Facade s3Facade;
    private final UserFacade userFacade;
    private final BoardFacade boardFacade;
    private final ReportFacade reportFacade;
    private final CommentFacade commentFacade;
    private final ReportRepository reportRepository;
    private final CommentRepository commentRepository;
    private final CommentReportRepository commentReportRepository;
    private final BoardRepository boardRepository;
    private final UserRepository userRepository;
    private final UserReportRepository userReportRepository;

    @Override
    @Transactional
    public void createReport(ReportRequest request, Long id) {
        User user = userFacade.getCurrentUser();
        Board board = boardFacade.getBoardById(id);

        Report report = new Report(
                request.getTitle(),
                request.getContent(),
                board.getUser(),
                board,
                user.getNickName()
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
                comment.getUser(),
                comment,
                user.getNickName()
        );
        commentReportRepository.save(report);
    }

    @Override
    @Transactional
    public void createUserReport(ReportRequest request, Long id){
        User user = userFacade.getUserById(id);
        User users = userFacade.getCurrentUser();

        userReportRepository.save(UserReport.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .user(user)
                .reporter(users.getNickName()).build());
    }

    @Override
    @Transactional
    public void delReport(Long id) {
        Report report = reportFacade.getReportById(id);
        reportRepository.delete(report);
    }

    @Override
    @Transactional
    public void delCommentReport(Long id) {
        CommentReport commentReport = reportFacade.getCommentReportById(id);
        commentReportRepository.delete(commentReport);
    }

    @Override
    @Transactional
    public void delUserReport(Long id){
        UserReport userReport = reportFacade.getUserReportById(id);
        userReportRepository.delete(userReport);
    }

    @Override
    @Transactional(readOnly = true)
    public ReportListResponse reportBoardList() {
        Sort sort = Sort.by(Sort.Direction.ASC, "id");
        List<Report> reports = reportFacade.getBoardAllById(sort);
        List<ReportList> reportLists = new ArrayList<>();

        for (Report report : reports) {
            ReportList dto = ReportList.builder()
                    .reportId(report.getId())
                    .title(report.getTitle())
                    .reporter(report.getReporter()).build();
            reportLists.add(dto);
        }
        return new ReportListResponse(reportLists);
    }

    @Override
    @Transactional(readOnly = true)
    public ReportListResponse reportCommentList() {
        Sort sort = Sort.by(Sort.Direction.ASC, "id");
        List<CommentReport> reports = reportFacade.getCommentAllById(sort);
        List<ReportList> reportLists = new ArrayList<>();

        for (CommentReport report : reports) {
            ReportList dto = ReportList.builder()
                    .reportId(report.getId())
                    .title(report.getTitle())
                    .reporter(report.getReporter()).build();
            reportLists.add(dto);
        }
        return new ReportListResponse(reportLists);
    }

    @Override
    @Transactional(readOnly = true)
    public ReportListResponse userReportList(){
        Sort sort = Sort.by(Sort.Direction.ASC, "id");
        List<UserReport> reports = reportFacade.getUserAllById(sort);
        List<ReportList> userReportLists = new ArrayList<>();

        for(UserReport report : reports){
            ReportList dto = ReportList.builder()
                    .reportId(report.getId())
                    .title(report.getTitle())
                    .reporter(report.getReporter()).build();
            userReportLists.add(dto);
        }
        return new ReportListResponse(userReportLists);
    }

    @Override
    @Transactional(readOnly = true)
    public ReportListResponse reportBoardFilterList(String title){
        List<Report> reports = reportFacade.getReportByTitle(title);
        List<ReportList> reportLists = new ArrayList<>();

        for (Report report : reports) {
            ReportList dto = ReportList.builder()
                    .reportId(report.getId())
                    .title(report.getTitle())
                    .reporter(report.getReporter()).build();
            reportLists.add(dto);
        }
        return new ReportListResponse(reportLists);
    }

    @Override
    @Transactional(readOnly = true)
    public ReportListResponse reportCommentFilterList(String title){
        List<CommentReport> reports = reportFacade.getCommentReportByTitle(title);
        List<ReportList> reportLists = new ArrayList<>();

        for (CommentReport report : reports) {
            ReportList dto = ReportList.builder()
                    .reportId(report.getId())
                    .title(report.getTitle())
                    .reporter(report.getReporter()).build();
            reportLists.add(dto);
        }
        return new ReportListResponse(reportLists);
    }

    @Override
    @Transactional(readOnly = true)
    public ReportListResponse userReportFilterList(String title){
        List<UserReport> reports = reportFacade.getUserReportByTitle(title);
        List<ReportList> reportLists = new ArrayList<>();

        for (UserReport report : reports) {
            ReportList dto = ReportList.builder()
                    .reportId(report.getId())
                    .title(report.getTitle())
                    .reporter(report.getReporter()).build();
            reportLists.add(dto);
        }
        return new ReportListResponse(reportLists);
    }

    @Override
    @Transactional(readOnly = true)
    public ReportResponse detailReport(Long id) {
        Report report = reportFacade.getReportById(id);

        return ReportResponse.builder()
                .reportId(report.getId())
                .reporter(report.getUser().getNickName())
                .title(report.getTitle())
                .content(report.getContent())
                .boardTitle(report.getBoard().getTitle())
                .boardContent(report.getBoard().getContent())
                .boardId(report.getBoard().getId())
                .imageUrl(boardImageNull(report.getBoard()))
                .reporter(report.getReporter()).build();
    }

    @Override
    @Transactional(readOnly = true)
    public CommentReportResponse detailCommentReport(Long id) {
        CommentReport report = reportFacade.getCommentReportById(id);

        return CommentReportResponse.builder()
                .reportId(report.getId())
                .reporter(report.getUser().getNickName())
                .title(report.getTitle())
                .content(report.getContent())
                .comment(report.getComment().getComments())
                .commentId(report.getComment().getId())
                .reporter(report.getReporter()).build();
    }

    @Override
    @Transactional(readOnly = true)
    public UserReportResponse detailUserReport(Long id){
        UserReport report = reportFacade.getUserReportById(id);

        return UserReportResponse.builder()
                .reportId(report.getId())
                .reporter(report.getReporter())
                .title(report.getTitle())
                .content(report.getContent())
                .userId(report.getUser().getId())
                .imageUrl(s3Facade.getUrl(report.getUser().getImageUrl()))
                .email(report.getUser().getEmail())
                .sex(report.getUser().getSex())
                .introduce(report.getUser().getIntroduction())
                .nickName(report.getUser().getNickName()).build();

    }

    @Override
    @Transactional
    public void forceDelBoard(Long id) {
        Board board = boardFacade.getBoardById(id);
        boardRepository.delete(board);
    }

    @Override
    @Transactional
    public void forceDelComment(Long id) {
        Comment comment = commentFacade.getChatById(id);
        commentRepository.delete(comment);
    }

    @Override
    @Transactional
    public void forceDelUser(Long id){
        User user = userFacade.getUserById(id);
        userRepository.delete(user);
    }

    public String boardImageNull(Board board){
        if(board.getImageUrl() == null){
            return null;
        }else return s3Facade.getUrl(board.getImageUrl());
    }

}
