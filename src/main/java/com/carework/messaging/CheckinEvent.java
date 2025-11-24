package com.carework.messaging;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

public record CheckinEvent(
        UUID checkinId,
        UUID userId,
        int mood,
        int stress,
        int sleepQuality,
        LocalDateTime createdAt
) implements Serializable {
}
