package com.example.todayisdiary.domain.report.repository;

import com.example.todayisdiary.domain.report.entity.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReportRepository extends JpaRepository<Report, Long> {
    Optional<Report> findReportById(Long id);

    @Query("select r from Report r where r.title LIKE %:title%")
    List<Report> findAllReportByTitleSearch(String title);
}
