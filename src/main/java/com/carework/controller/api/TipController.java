package com.carework.controller.api;

import com.carework.dto.CreateTipDTO;
import com.carework.dto.TipDTO;
import com.carework.service.DtoMapper;
import com.carework.service.TipService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/tips")
@RequiredArgsConstructor
public class TipController {

    private final TipService tipService;

    @PostMapping
    public ResponseEntity<TipDTO> create(@Valid @RequestBody CreateTipDTO dto) {
        return ResponseEntity.ok(DtoMapper.toTipDTO(tipService.create(dto)));
    }

    @GetMapping
    public ResponseEntity<List<TipDTO>> list() {
        List<TipDTO> response = tipService.list().stream()
                .map(DtoMapper::toTipDTO)
                .toList();
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        tipService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
