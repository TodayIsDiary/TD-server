package com.example.todayisdiary.domain.comment.controller;

import com.example.todayisdiary.domain.comment.dto.CommentRequest;
import com.example.todayisdiary.domain.comment.dto.CommentResponseList;
import com.example.todayisdiary.domain.comment.service.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Tag(name = "comment", description = "댓글에 대한 API 입니다.")
@RestController
@RequiredArgsConstructor
@RequestMapping("/comment")
public class CommentController {
    private final CommentService commentService;

    @Operation(description = "댓글 생성")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/create/{board_id}")
    public void createChat(@PathVariable(name = "board_id") Long id, @RequestBody CommentRequest request) {
        commentService.createChat(request, id);
    }

    @Operation(description = "답글 생성")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/reply/create/{chat_id}")
    public void createReplyChat(@PathVariable(name = "chat_id") Long id, @RequestBody CommentRequest request) {
        commentService.createReplyChat(request, id);
    }

    @Operation(description = "댓글 수정")
    @PatchMapping("/modify/{comment_id}")
    public void setChat(@PathVariable(name = "comment_id") Long id, @RequestBody CommentRequest request) {
        commentService.setChat(request, id);
    }

    @Operation(description = "댓글 삭제")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/delete/{comment_id}")
    public void delChat(@PathVariable(name = "comment_id") Long id) {
        commentService.deleteChat(id);
    }

    @Operation(description = "댓글 보기")
    @GetMapping("/list/{board_id}")
    public CommentResponseList chatLists(@PathVariable(name = "board_id") Long id) {
        return commentService.chatList(id);
    }

    @Operation(description = "답글 보기")
    @GetMapping("/list/reply/{comment_id}")
    public CommentResponseList chatReplyLists(@PathVariable(name = "comment_id") Long id) {
        return commentService.chatReplyList(id);
    }

    @Operation(description = "인기 많은 댓글 보기")
    @GetMapping("/list/heart/{board_id}")
    public CommentResponseList chatHeartList(@PathVariable(name = "board_id") Long id) {
        return commentService.chatHeartList(id);
    }
}
