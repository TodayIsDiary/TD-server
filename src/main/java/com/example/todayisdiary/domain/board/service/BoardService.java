package com.example.todayisdiary.domain.board.service;

import com.example.todayisdiary.domain.board.dto.BoardList;
import com.example.todayisdiary.domain.board.dto.BoardRequest;
import com.example.todayisdiary.domain.board.dto.BoardResponse;
import com.example.todayisdiary.domain.board.enums.BoardCategory;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface BoardService {
    void createBoard(BoardRequest boardRequest);

    void setBoard(BoardRequest boardRequest, Long id);

    void delBoard(Long id);

    List<BoardList> boardLists();

    List<BoardList> boardCategoryList(BoardCategory category);

    List<BoardList> myPost();

    BoardResponse boardDetail(Long id);
}
