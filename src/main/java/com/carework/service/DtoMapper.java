package com.carework.service;

import com.carework.dto.MoodCheckinDTO;
import com.carework.dto.TipDTO;
import com.carework.dto.UserDTO;
import com.carework.model.MoodCheckin;
import com.carework.model.Tip;
import com.carework.model.User;

public final class DtoMapper {

    private DtoMapper() {
    }

    public static UserDTO toUserDTO(User user) {
        return new UserDTO(user.getId(), user.getName(), user.getEmail(), user.getTeam());
    }

    public static MoodCheckinDTO toMoodCheckinDTO(MoodCheckin checkin) {
        return new MoodCheckinDTO(
                checkin.getId(),
                checkin.getUser().getId(),
                checkin.getMood(),
                checkin.getStress(),
                checkin.getSleepQuality(),
                checkin.getCreatedAt()
        );
    }

    public static TipDTO toTipDTO(Tip tip) {
        return new TipDTO(tip.getId(), tip.getTitle(), tip.getDescription());
    }
}
