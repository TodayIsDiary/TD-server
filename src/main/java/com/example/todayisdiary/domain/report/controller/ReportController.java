package com.example.todayisdiary.domain.report.controller;

import com.example.todayisdiary.domain.report.dto.*;
import com.example.todayisdiary.domain.report.dto.request.ReportRequest;
import com.example.todayisdiary.domain.report.service.ReportService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Tag(name = "report", description = "신고에 대한 API 입니다.")
@RestController
@RequestMapping("/report")
@RequiredArgsConstructor
public class ReportController {
    private final ReportService reportService;

    @Operation(description = "일기 신고 생성")
    @PostMapping("/diary/{diary_id}")
    @ResponseStatus(HttpStatus.CREATED)
    public void createReport(@RequestBody ReportRequest request, @PathVariable(name = "diary_id") Long id){
        reportService.createReport(request, id);
    }

    @Operation(description = "댓글 신고 생성")
    @PostMapping("/comment/{comment_id}")
    @ResponseStatus(HttpStatus.CREATED)
    public void createCommentReport(@RequestBody ReportRequest request,@PathVariable(name = "comment_id") Long id){
        reportService.createCommentReport(request, id);
    }

    @Operation(description = "유저 신고 생성")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/user/{user_id}")
    public void createUserReport(@RequestBody ReportRequest request, @PathVariable(name = "user_id") Long id){
        reportService.createUserReport(request,id);
    }

    @Operation(description = "신고당한 일기 리스트")
    @GetMapping("/list/diary")
    public ReportListResponse reportBoardList(){
        return reportService.reportBoardList();
    }

    @Operation(description = "신고당한 댓글 리스트")
    @GetMapping("/list/comment")
    public ReportListResponse reportCommentList(){
        return reportService.reportCommentList();
    }

    @Operation(description = "신고당한 유저 리스트")
    @GetMapping("/list/user")
    public ReportListResponse reportUserList(){return reportService.userReportList();}

    @Operation(description = "신고당한 일기 필터 리스트")
    @GetMapping("/list/diary/filter")
    public ReportListResponse responseBoardFilterList(@RequestParam String title){
        return reportService.reportBoardFilterList(title);
    }

    @Operation(description = "신고당한 댓글 필터 리스트")
    @GetMapping("/list/comment/filter")
    public ReportListResponse reportCommentFilterList(@RequestParam String title){
        return reportService.reportCommentFilterList(title);
    }

    @Operation(description = "신고당한 유저 필터 리스트")
    @GetMapping("/list/user/filter")
    public ReportListResponse reportUserFilterList(@RequestParam String title){
        return reportService.userReportFilterList(title);
    }

    @Operation(description = "신고 일기 자세히보기")
    @GetMapping("/detail/diary/{report_id}")
    public ReportResponse reportBoardDetail(@PathVariable(name = "report_id") Long id){
        return reportService.detailReport(id);
    }

    @Operation(description = "신고 댓글 자세히보기")
    @GetMapping("/detail/comment/{report_id}")
    public CommentReportResponse reportCommentDetail(@PathVariable(name = "report_id") Long id){
        return reportService.detailCommentReport(id);
    }

    @Operation(description = "신고 유저 자세히보기")
    @GetMapping("/detail/user/{report_id}")
    public UserReportResponse reportUserDetail(@PathVariable(name = "report_id") Long id){
        return reportService.detailUserReport(id);
    }

    @Operation(description = "허위 일기 신고 삭제하기")
    @DeleteMapping("/del/false/diary/{report_id}")
    public void reportBoardDel(@PathVariable(name = "report_id") Long id){
        reportService.delReport(id);
    }

    @Operation(description = "허위 댓글 신고 삭제하기")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/del/false/comment/{report_id}")
    public void reportCommentDel(@PathVariable(name = "report_id") Long id){
        reportService.delCommentReport(id);
    }

    @Operation(description = "허위 유저 신고 삭제하기")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/del/false/user/{report_id}")
    public void reportUserDel(@PathVariable(name = "report_id") Long id){reportService.delUserReport(id);}

    @Operation(description = "부적절한 일기 삭제하기")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/del/diary/{diary_id}")
    public void boardDel(@PathVariable(name = "diary_id") Long id){
        reportService.forceDelBoard(id);
    }

    @Operation(description = "부적절한 댓글 삭제하기")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/del/comment/{comment_id}")
    public void commentDel(@PathVariable(name = "comment_id") Long id){
        reportService.forceDelComment(id);
    }

    @Operation(description = "부적절한 유저 삭제하기")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/del/user/{user_id}")
    public void userDel(@PathVariable(name = "user_id") Long id){reportService.forceDelUser(id);}
}
