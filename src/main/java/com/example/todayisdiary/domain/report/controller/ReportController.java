package com.example.todayisdiary.domain.report.controller;

import com.example.todayisdiary.domain.report.dto.ReportList;
import com.example.todayisdiary.domain.report.dto.ReportRequest;
import com.example.todayisdiary.domain.report.dto.ReportResponse;
import com.example.todayisdiary.domain.report.service.ReportService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "report", description = "신고에 대한 API 입니다.")
@RestController
@RequestMapping("/report")
@RequiredArgsConstructor
public class ReportController {
    private final ReportService reportService;

    @Operation(description = "신고 생성")
    @PostMapping("/board/{board_id}")
    @ResponseStatus(HttpStatus.CREATED)
    public void createReport(@RequestBody ReportRequest request,@PathVariable(name = "board_id") Long id){
        reportService.createReport(request, id);
    }

    @Operation(description = "신고당한 일기 리스트")
    @GetMapping("/list/board")
    public List<ReportList> reportBoardList(){
        return reportService.reportBoardList();
    }

    @Operation(description = "신고 일기 자세히보기")
    @GetMapping("/detail/board/{board_id}")
    public ReportResponse reportBoardDetail(@PathVariable(name = "board_id") Long id){
        return reportService.detailReport(id);
    }

    @Operation(description = "허위신고 삭제하기")
    @DeleteMapping("/del/{report_id}")
    public void reportDel(@PathVariable(name = "report_id") Long id){
        reportService.delReport(id);
    }

    @Operation(description = "부적절한 일기 삭제하기")
    @DeleteMapping("/del/board/{board_id}")
    public void boardDel(@PathVariable(name = "board_id") Long id){
        reportService.forceDelBoard(id);
    }
}
