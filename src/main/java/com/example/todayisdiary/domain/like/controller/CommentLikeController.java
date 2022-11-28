package com.example.todayisdiary.domain.like.controller;

import com.example.todayisdiary.domain.like.service.CommentLikeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Tag(name = "comment_like", description = "댓글 좋아요 API 입니다.")
@RestController
@RequiredArgsConstructor
@RequestMapping("/comment")
public class CommentLikeController {
    private final CommentLikeService commentLikeService;

    @Operation(description = "좋아요 누르기")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PostMapping("/like/{comment_id}")
    public void wishPost(@PathVariable(name = "comment_id") Long commentId) {
        commentLikeService.commentLike(commentId);
    }

    @Operation(description = "좋아요 취소")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/like/cancel/{comment_id}")
    public void wishCancel(@PathVariable(name = "comment_id") Long commentId) {
        commentLikeService.commentLikeCancel(commentId);
    }
}
