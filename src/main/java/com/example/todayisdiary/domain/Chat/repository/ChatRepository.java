package com.example.todayisdiary.domain.Chat.repository;

import com.example.todayisdiary.domain.Chat.entity.Chat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChatRepository extends JpaRepository<Chat, Long> {
    Optional<Chat> findChatById(Long id);

    List<Chat> findAllById(Long id);
}
