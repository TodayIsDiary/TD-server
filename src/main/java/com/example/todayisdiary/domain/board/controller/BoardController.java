package com.example.todayisdiary.domain.board.controller;

import com.example.todayisdiary.domain.board.dto.BoardRequest;
import com.example.todayisdiary.domain.board.dto.BoardResponse;
import com.example.todayisdiary.domain.board.dto.BoardResponseList;
import com.example.todayisdiary.domain.board.enums.BoardCategory;
import com.example.todayisdiary.domain.board.service.BoardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Tag(name = "diary", description = "일기에 대한 API 입니다.")
@RestController
@RequestMapping("/diary")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @Operation(description = "일기 생성하기")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/create")
    public void boardLists(@RequestBody BoardRequest request){
        boardService.createBoard(request);
    }

    @Operation(description = "일기 상세보기")
    @GetMapping("/detail/{diary_id}")
    public BoardResponse boardDetail(@PathVariable(name = "diary_id") Long id){
        return boardService.boardDetail(id);
    }

    @Operation(description = "일기 수정하기")
    @PutMapping("/modify/{diary_id}")
    public void boardModify(@RequestBody BoardRequest request, @PathVariable(name = "diary_id") Long id){
        boardService.setBoard(request, id);
    }

    @Operation(description = "일기 삭제하기")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/delete/{diary_id}")
    public void boardDelete(@PathVariable(name = "diary_id") Long id){
        boardService.delBoard(id);
    }

    @Operation(description = "일기 리스트 형식으로 보기")
    @GetMapping("/list")
    public BoardResponseList boardList(){
        return boardService.boardLists();
    }

    @Operation(description = "일기 카테고리 리스트 형식으로 보기")
    @GetMapping("/list/category")
    public BoardResponseList boardCategoryList(@RequestParam("value") BoardCategory boardCategory){
        return boardService.boardCategoryList(boardCategory);
    }

    @Operation(description = "인기 일기 리스트 형식으로 보기")
    @GetMapping("/list/heart")
    public BoardResponseList boardHeartList(){
        return boardService.boardHeartList();
    }

    @Operation(description = "자신의 쓸 일기 보기")
    @GetMapping("/my")
    public BoardResponseList myBoard(){
        return boardService.myPost();
    }
}
