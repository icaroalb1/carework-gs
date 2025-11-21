package com.carework.dto;

import java.time.LocalDate;
import java.util.UUID;

public record WeeklyReportDTO(
        UUID userId,
        LocalDate startDate,
        LocalDate endDate,
        double averageMood,
        double averageStress,
        double averageSleepQuality,
        String aiMessage
) {
}
