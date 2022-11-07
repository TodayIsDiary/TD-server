package com.example.todayisdiary.domain.board.facade;

import com.example.todayisdiary.domain.board.entity.Board;
import com.example.todayisdiary.domain.board.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class BoardFacade {

    private final BoardRepository boardRepository;

    public Board getBoardById(Long id){
        return boardRepository.findBoardById(id)
                .orElseThrow(() -> new IllegalArgumentException("일기를 찾을 수 없습니다."));
    }

    public List<Board> getBoardAllById(Sort sort){
        return boardRepository.findAll(sort);
    }

}
