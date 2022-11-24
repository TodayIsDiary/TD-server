package com.example.todayisdiary.domain.like.repository;

import com.example.todayisdiary.domain.board.entity.Board;
import com.example.todayisdiary.domain.like.entity.BoardLove;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BoardLikeRepository extends JpaRepository<BoardLove, Long> {
    Optional<BoardLove> findBoardLikeByBoard(Board board);
}
