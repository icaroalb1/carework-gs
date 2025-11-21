package com.carework.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record CheckinEventLogDTO(Long id, Long checkinId, UUID userId, String summary, LocalDateTime createdAt) {
}
