package com.carework.controller.api;

import com.carework.dto.CheckinEventLogDTO;
import com.carework.repository.CheckinEventLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/events")
@RequiredArgsConstructor
public class EventController {

    private final CheckinEventLogRepository repository;

    @GetMapping
    public ResponseEntity<Page<CheckinEventLogDTO>> list(@PageableDefault(size = 10) Pageable pageable) {
        Page<CheckinEventLogDTO> page = repository.findAll(pageable)
                .map(log -> new CheckinEventLogDTO(log.getId(), log.getCheckinId(), log.getUserId(), log.getSummary(), log.getCreatedAt()));
        return ResponseEntity.ok(page);
    }
}
