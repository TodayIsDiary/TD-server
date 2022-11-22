package com.example.todayisdiary.domain.Chat.controller;

import com.example.todayisdiary.domain.Chat.dto.ChatRequest;
import com.example.todayisdiary.domain.Chat.dto.ChatResponseList;
import com.example.todayisdiary.domain.Chat.service.ChatService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Tag(name = "comment", description = "댓글에 대한 API 입니다.")
@RestController
@RequiredArgsConstructor
@RequestMapping("/comment")
public class ChatController {
    private final ChatService chatService;

    @Operation(description = "채팅 생성")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/create/{board_id}")
    public void createChat(@PathVariable(name = "board_id") Long id, @RequestBody ChatRequest request){
        chatService.createChat(request, id);
    }

    @Operation(description = "답글 생성")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/reply/create/{chat_id}")
    public void createReplyChat(@PathVariable(name = "chat_id") Long id, @RequestBody ChatRequest request){
        chatService.createReplyChat(request, id);
    }

    @Operation(description = "채팅 수정")
    @PatchMapping("/modify/{comment_id}")
    public void setChat(@PathVariable(name = "comment_id") Long id, @RequestBody ChatRequest request){
        chatService.setChat(request, id);
    }

    @Operation(description = "채팅 삭제")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/delete/{comment_id}")
    public void delChat(@PathVariable(name = "comment_id") Long id){
        chatService.deleteChat(id);
    }

    @Operation(description = "채팅 보기")
    @GetMapping("/list/{board_id}")
    public ChatResponseList chatLists(@PathVariable(name = "board_id") Long id){
        return chatService.chatList(id);
    }

    @Operation(description = "답글 보기")
    @GetMapping("/list/reply/{comment_id}")
    public ChatResponseList chatReplyLists(@PathVariable(name = "comment_id") Long id){
        return chatService.chatReplyList(id);
    }
}
