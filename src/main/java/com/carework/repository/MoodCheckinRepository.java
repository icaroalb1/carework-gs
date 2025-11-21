package com.carework.repository;

import com.carework.model.MoodCheckin;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface MoodCheckinRepository extends JpaRepository<MoodCheckin, Long> {
    boolean existsByUserIdAndCreatedAtBetween(UUID userId, LocalDateTime start, LocalDateTime end);

    List<MoodCheckin> findByUserIdOrderByCreatedAtDesc(UUID userId);

    List<MoodCheckin> findByUserIdAndCreatedAtBetween(UUID userId, LocalDateTime start, LocalDateTime end);

    Page<MoodCheckin> findAllByOrderByCreatedAtDesc(Pageable pageable);
}
