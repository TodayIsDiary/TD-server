package com.example.todayisdiary.domain.report.facade;

import com.example.todayisdiary.domain.report.entity.Report;
import com.example.todayisdiary.domain.report.repository.ReportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ReportFacade {
    private final ReportRepository reportRepository;

    public Report getReportById(Long id) {
        return reportRepository.findReportById(id)
                .orElseThrow(() -> new IllegalArgumentException("신고내용을 찾을 수 없습니다."));
    }

    public List<Report> getBoardAllById(Sort sort){
        return reportRepository.findAll(sort);
    }
}
