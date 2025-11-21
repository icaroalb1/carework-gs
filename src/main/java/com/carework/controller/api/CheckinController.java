package com.carework.controller.api;

import com.carework.dto.CreateCheckinDTO;
import com.carework.dto.MoodCheckinDTO;
import com.carework.service.CheckinService;
import com.carework.service.DtoMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/checkins")
@RequiredArgsConstructor
public class CheckinController {

    private final CheckinService checkinService;

    @PostMapping
    public ResponseEntity<MoodCheckinDTO> create(@Valid @RequestBody CreateCheckinDTO dto) {
        var checkin = checkinService.createCheckin(dto);
        return ResponseEntity.ok(DtoMapper.toMoodCheckinDTO(checkin));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<MoodCheckinDTO>> listByUser(@PathVariable UUID userId) {
        List<MoodCheckinDTO> response = checkinService.listByUser(userId).stream()
                .map(DtoMapper::toMoodCheckinDTO)
                .toList();
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<Page<MoodCheckinDTO>> list(@PageableDefault(size = 10) Pageable pageable) {
        Page<MoodCheckinDTO> response = checkinService.listAll(pageable)
                .map(DtoMapper::toMoodCheckinDTO);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/user/{userId}/today")
    public ResponseEntity<Void> deleteTodayCheckins(@PathVariable UUID userId) {
        checkinService.deleteTodayCheckins(userId);
        return ResponseEntity.noContent().build();
    }
}
