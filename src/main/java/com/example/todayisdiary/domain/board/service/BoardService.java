package com.example.todayisdiary.domain.board.service;

import com.example.todayisdiary.domain.board.dto.BoardRequest;
import com.example.todayisdiary.domain.board.dto.BoardResponse;
import com.example.todayisdiary.domain.board.dto.BoardResponseList;
import com.example.todayisdiary.domain.board.enums.BoardCategory;

public interface BoardService {
    void createBoard(BoardRequest boardRequest);

    void setBoard(BoardRequest boardRequest, Long id);

    void delBoard(Long id);

    BoardResponseList boardLists();

    BoardResponseList boardCategoryList(BoardCategory category);

    BoardResponseList boardHeartList();

    BoardResponseList myPost();

    BoardResponse boardDetail(Long id);
}
