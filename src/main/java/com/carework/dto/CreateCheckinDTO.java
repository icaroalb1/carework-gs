package com.carework.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record CreateCheckinDTO(
        @NotNull UUID userId,
        @Min(1) @Max(5) int mood,
        @Min(1) @Max(5) int stress,
        @Min(1) @Max(5) int sleepQuality
) {
}
