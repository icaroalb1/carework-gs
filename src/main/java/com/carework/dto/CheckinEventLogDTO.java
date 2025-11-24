package com.carework.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record CheckinEventLogDTO(UUID id, UUID checkinId, UUID userId, String summary, LocalDateTime createdAt) {
}
