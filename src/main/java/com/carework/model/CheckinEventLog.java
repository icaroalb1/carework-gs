package com.carework.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "checkin_event_logs")
@Getter
@Setter
public class CheckinEventLog {

    @Id
    private UUID id;

    private UUID checkinId;
    private UUID userId;
    private String summary;
    private LocalDateTime createdAt;

    @PrePersist
    void prePersist() {
        if (id == null) {
            id = UUID.randomUUID();
        }
    }
}
