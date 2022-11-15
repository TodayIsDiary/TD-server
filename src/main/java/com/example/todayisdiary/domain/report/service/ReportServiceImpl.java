package com.example.todayisdiary.domain.report.service;

import com.example.todayisdiary.domain.board.entity.Board;
import com.example.todayisdiary.domain.board.facade.BoardFacade;
import com.example.todayisdiary.domain.board.repository.BoardRepository;
import com.example.todayisdiary.domain.report.dto.ReportList;
import com.example.todayisdiary.domain.report.dto.ReportRequest;
import com.example.todayisdiary.domain.report.dto.ReportResponse;
import com.example.todayisdiary.domain.report.entity.Report;
import com.example.todayisdiary.domain.report.facade.ReportFacade;
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
    private final ReportRepository reportRepository;
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
    public void delReport(Long id) {
        adminAccount();
        Report report = reportFacade.getReportById(id);
        reportRepository.delete(report);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ReportList> reportBoardList() {
        adminAccount();
        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        List<Report> reports = reportFacade.getBoardAllById(sort);
        List<ReportList> reportLists = new ArrayList<>();

        for (Report report : reports) {
            ReportList dto = ReportList.builder()
                    .title(report.getTitle())
                    .boardId(report.getBoard().getId())
                    .reporter(report.getUser().getNickName()).build();
            reportLists.add(dto);
        }
        return reportLists;
    }

    @Override
    @Transactional(readOnly = true)
    public ReportResponse detailReport(Long id) {
        adminAccount();
        Report report = reportFacade.getReportById(id);

        return ReportResponse.builder()
                .reporter(report.getUser().getNickName())
                .title(report.getTitle())
                .content(report.getContent())
                .boardTitle(report.getBoard().getTitle())
                .boardContent(report.getBoard().getContent()).build();
    }

    @Override
    @Transactional
    public void forceDelBoard(Long id) {
        adminAccount();
        Board board = boardFacade.getBoardById(id);
        boardRepository.delete(board);
    }

    private void adminAccount() {
        if (userFacade.getCurrentUser().getRole() != Role.ADMIN) throw new IllegalStateException("어드민 계정이 아닙니다.");
    }

}
