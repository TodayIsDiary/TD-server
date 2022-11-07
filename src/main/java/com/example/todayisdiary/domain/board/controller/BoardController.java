package com.example.todayisdiary.domain.board.controller;

import com.example.todayisdiary.domain.board.dto.BoardList;
import com.example.todayisdiary.domain.board.dto.BoardRequest;
import com.example.todayisdiary.domain.board.dto.BoardResponse;
import com.example.todayisdiary.domain.board.enums.BoardCategory;
import com.example.todayisdiary.domain.board.service.BoardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    @GetMapping("/detail/{diaryId}")
    public BoardResponse boardDetail(@PathVariable(name = "diaryId") Long id){
        return boardService.boardDetail(id);
    }

    @Operation(description = "일기 수정하기")
    @PutMapping("/modify/{diaryId}")
    public void boardModify(@RequestBody BoardRequest request, @PathVariable(name = "diaryId") Long id){
        boardService.setBoard(request, id);
    }

    @Operation(description = "일기 삭제하기")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/delete/{diaryId}")
    public void boardDelete(@PathVariable(name = "diaryId") Long id){
        boardService.delBoard(id);
    }

    @Operation(description = "일기 리스트 형식으로 보기")
    @GetMapping("/list")
    public List<BoardList> boardList(){
        return boardService.boardLists();
    }

    @Operation(description = "일기 카테고리 리스트 형식으로 보기")
    @GetMapping("/list/category")
    public List<BoardList> boardCategoryList(@RequestParam("value") BoardCategory boardCategory){
        return boardService.boardCategoryList(boardCategory);
    }

    @Operation(description = "자신의 쓸 일기 보기")
    @GetMapping("/my")
    public List<BoardList> myBoard(){
        return boardService.myPost();
    }
}
