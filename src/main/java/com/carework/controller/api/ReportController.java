package com.carework.controller.api;

import com.carework.dto.WeeklyReportDTO;
import com.carework.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/reports")
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;

    @GetMapping("/weekly/{userId}")
    public ResponseEntity<WeeklyReportDTO> weekly(@PathVariable UUID userId) {
        return ResponseEntity.ok(reportService.buildWeeklyReport(userId));
    }
}
