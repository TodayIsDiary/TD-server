package com.example.todayisdiary.domain.like.repository;

import com.example.todayisdiary.domain.comment.entity.Comment;
import com.example.todayisdiary.domain.like.entity.CommentLove;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CommentLikeRepository extends JpaRepository<CommentLove, Long> {
    Optional<CommentLove> findCommentLoveByComment(Comment comment);
}
