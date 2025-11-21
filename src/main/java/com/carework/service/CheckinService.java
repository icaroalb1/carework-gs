package com.carework.service;

import com.carework.dto.CreateCheckinDTO;
import com.carework.exception.BusinessException;
import com.carework.messaging.CheckinEvent;
import com.carework.messaging.CheckinEventPublisher;
import com.carework.model.MoodCheckin;
import com.carework.model.User;
import com.carework.repository.MoodCheckinRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CheckinService {

    private final MoodCheckinRepository moodCheckinRepository;
    private final UserService userService;
    private final CheckinEventPublisher eventPublisher;

    @Transactional
    public MoodCheckin createCheckin(CreateCheckinDTO dto) {
        // Validação de check-in diário removida para permitir múltiplos check-ins
        User user = userService.findById(dto.userId());
        MoodCheckin checkin = new MoodCheckin();
        checkin.setUser(user);
        checkin.setMood(dto.mood());
        checkin.setStress(dto.stress());
        checkin.setSleepQuality(dto.sleepQuality());
        checkin.setCreatedAt(LocalDateTime.now());
        MoodCheckin saved = moodCheckinRepository.save(checkin);
        eventPublisher.publish(new CheckinEvent(
                saved.getId(),
                user.getId(),
                saved.getMood(),
                saved.getStress(),
                saved.getSleepQuality(),
                saved.getCreatedAt()
        ));
        return saved;
    }

    /**
     * Método mantido para possível uso futuro, mas não é mais chamado automaticamente.
     * Permite múltiplos check-ins por dia para facilitar testes e desenvolvimento.
     */
    @Deprecated
    public void validateDailyCheckin(UUID userId) {
        LocalDateTime start = LocalDate.now().atStartOfDay();
        LocalDateTime end = start.plusDays(1);
        boolean exists = moodCheckinRepository.existsByUserIdAndCreatedAtBetween(userId, start, end);
        if (exists) {
            throw new BusinessException("Usuário já realizou check-in hoje");
        }
    }

    @Transactional(readOnly = true)
    public List<MoodCheckin> listByUser(UUID userId) {
        return moodCheckinRepository.findByUserIdOrderByCreatedAtDesc(userId);
    }

    @Transactional(readOnly = true)
    public Page<MoodCheckin> listAll(Pageable pageable) {
        return moodCheckinRepository.findAllByOrderByCreatedAtDesc(pageable);
    }

    @Transactional
    public void deleteTodayCheckins(UUID userId) {
        LocalDateTime start = LocalDate.now().atStartOfDay();
        LocalDateTime end = start.plusDays(1);
        List<MoodCheckin> todayCheckins = moodCheckinRepository.findByUserIdAndCreatedAtBetween(userId, start, end);
        moodCheckinRepository.deleteAll(todayCheckins);
    }
}
