package com.example.todayisdiary.domain.board.repository;

import com.example.todayisdiary.domain.board.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {

    Optional<Board> findBoardById(Long id);
}
