package com.carework.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record MoodCheckinDTO(
        UUID id,
        UUID userId,
        int mood,
        int stress,
        int sleepQuality,
        LocalDateTime createdAt
) {
}
