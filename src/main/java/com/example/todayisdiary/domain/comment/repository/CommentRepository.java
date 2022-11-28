package com.example.todayisdiary.domain.comment.repository;

import com.example.todayisdiary.domain.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    Optional<Comment> findChatById(Long id);

    List<Comment> findCommentByOriginChatId(Long id);
}
