package com.carework.service;

import com.carework.dto.CreateTipDTO;
import com.carework.exception.ResourceNotFoundException;
import com.carework.model.Tip;
import com.carework.repository.TipRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TipService {

    private final TipRepository tipRepository;

    @Transactional
    @CacheEvict(value = "tips", allEntries = true)
    public Tip create(CreateTipDTO dto) {
        Tip tip = new Tip();
        tip.setTitle(dto.title());
        tip.setDescription(dto.description());
        return tipRepository.save(tip);
    }

    @Transactional(readOnly = true)
    @Cacheable("tips")
    public List<Tip> list() {
        return tipRepository.findAll();
    }

    @Transactional
    @CacheEvict(value = "tips", allEntries = true)
    public void delete(Long id) {
        Tip tip = tipRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Dica não encontrada"));
        tipRepository.delete(tip);
    }
}
