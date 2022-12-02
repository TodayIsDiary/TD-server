package com.example.todayisdiary.domain.report.repository;

import com.example.todayisdiary.domain.report.entity.UserReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserReportRepository extends JpaRepository<UserReport, Long> {
    Optional<UserReport> findUserReportById(Long id);

    @Query("select u from UserReport u where u.title LIKE %:title%")
    List<UserReport> findUserReportsByTitleSearch(String title);
}
