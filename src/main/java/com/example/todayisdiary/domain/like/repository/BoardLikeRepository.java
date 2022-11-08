package com.example.todayisdiary.domain.like.repository;

import com.example.todayisdiary.domain.board.entity.Board;
import com.example.todayisdiary.domain.like.entity.BoardLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BoardLikeRepository extends JpaRepository<BoardLike, Long> {
    Optional<BoardLike> findBoardLikeByBoard(Board board);
}
