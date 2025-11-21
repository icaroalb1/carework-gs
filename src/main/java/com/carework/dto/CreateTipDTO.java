package com.carework.dto;

import jakarta.validation.constraints.NotBlank;

public record CreateTipDTO(@NotBlank String title, @NotBlank String description) {
}
