package com.carework.messaging;

import com.carework.model.CheckinEventLog;
import com.carework.repository.CheckinEventLogRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
@Slf4j
public class CheckinEventListener {

    private final CheckinEventLogRepository eventLogRepository;

    @ServiceActivator(inputChannel = "checkinEventsChannel")
    public void consume(CheckinEvent event) {
        log.info("Mensageria: recebendo check-in {} do usuário {}", event.checkinId(), event.userId());
        CheckinEventLog logEntry = new CheckinEventLog();
        logEntry.setCheckinId(event.checkinId());
        logEntry.setUserId(event.userId());
        logEntry.setSummary(buildSummary(event));
        logEntry.setCreatedAt(LocalDateTime.now());
        eventLogRepository.save(logEntry);
    }

    private String buildSummary(CheckinEvent event) {
        return String.format("Check-in %d | Humor: %d | Estresse: %d | Sono: %d",
                event.checkinId(), event.mood(), event.stress(), event.sleepQuality());
    }
}
