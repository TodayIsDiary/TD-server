package com.example.todayisdiary.domain.report.repository;

import com.example.todayisdiary.domain.report.entity.CommentReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentReportRepository extends JpaRepository<CommentReport, Long> {
    Optional<CommentReport> findCommentReportById(Long id);

    @Query("select c from CommentReport c where c.title LIKE %:title%")
    List<CommentReport> findAllCommentReportByTitleSearch(String title);
}
