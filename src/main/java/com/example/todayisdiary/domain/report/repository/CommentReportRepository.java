package com.example.todayisdiary.domain.report.repository;

import com.example.todayisdiary.domain.report.entity.CommentReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CommentReportRepository extends JpaRepository<CommentReport, Long> {
    Optional<CommentReport> findCommentReportById(Long id);
}
