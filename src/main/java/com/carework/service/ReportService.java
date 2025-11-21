package com.carework.service;

import com.carework.dto.WeeklyReportDTO;
import com.carework.model.MoodCheckin;
import com.carework.repository.MoodCheckinRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReportService {

    private final MoodCheckinRepository moodCheckinRepository;
    private final AiService aiService;

    @Transactional(readOnly = true)
    public WeeklyReportDTO buildWeeklyReport(UUID userId) {
        LocalDate end = LocalDate.now();
        LocalDate start = end.minusDays(6);
        LocalDateTime startDateTime = start.atStartOfDay();
        LocalDateTime endDateTime = end.plusDays(1).atStartOfDay();
        List<MoodCheckin> checkins = moodCheckinRepository.findByUserIdAndCreatedAtBetween(userId, startDateTime, endDateTime);

        double avgMood = checkins.stream().mapToInt(MoodCheckin::getMood).average().orElse(0d);
        double avgStress = checkins.stream().mapToInt(MoodCheckin::getStress).average().orElse(0d);
        double avgSleep = checkins.stream().mapToInt(MoodCheckin::getSleepQuality).average().orElse(0d);

        String aiMessage = aiService.generateMessage(avgMood, avgStress, avgSleep);

        return new WeeklyReportDTO(userId, start, end, avgMood, avgStress, avgSleep, aiMessage);
    }
}
