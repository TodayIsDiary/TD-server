package com.example.todayisdiary.domain.comment.repository;

import com.example.todayisdiary.domain.comment.entity.Chat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<Chat, Long> {
    Optional<Chat> findChatById(Long id);

    List<Chat> findAllById(Long id);
}
