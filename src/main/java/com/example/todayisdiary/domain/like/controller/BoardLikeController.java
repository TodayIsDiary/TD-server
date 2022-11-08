package com.example.todayisdiary.domain.like.controller;

import com.example.todayisdiary.domain.like.service.LikeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/diary")
@Tag(name = "like", description = "좋아요에 대한 API 입니다.")
public class BoardLikeController {
    private final LikeService likeService;

    @Operation(description = "좋아요 누르기")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PostMapping("/like/{diary_id}")
    public void wishPost(@PathVariable(name = "diary_id") Long postId) {
        likeService.boardLike(postId);
    }

    @Operation(description = "좋아요 취소")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/like/cancel/{diary_id}")
    public void wishCancel(@PathVariable(name = "diary_id") Long postId) {
        likeService.boardLikeCancel(postId);
    }
}
