package com.carework.controller.web;

import com.carework.config.CareworkProperties;
import com.carework.dto.MoodCheckinDTO;
import com.carework.dto.WeeklyReportDTO;
import com.carework.service.InternalApiClient;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class WebHomeController {

    private final InternalApiClient apiClient;
    private final CareworkProperties properties;

    @GetMapping("/")
    public String root() {
        return "redirect:/home";
    }

    @GetMapping({"/home"})
    public String home(Model model) {
        UUID userId = properties.getDemo().getUserId();
        List<MoodCheckinDTO> checkins = apiClient.get("/checkins/user/" + userId,
                new ParameterizedTypeReference<>() {});
        if (checkins == null) {
            checkins = Collections.emptyList();
        }
        MoodCheckinDTO lastCheckin = checkins.stream().findFirst().orElse(null);
        WeeklyReportDTO report = apiClient.get("/reports/weekly/" + userId, WeeklyReportDTO.class);

        model.addAttribute("lastCheckin", lastCheckin);
        model.addAttribute("allCheckins", checkins); // Adiciona todos os check-ins
        model.addAttribute("checkinsCount", checkins.size()); // Contador
        model.addAttribute("report", report);
        model.addAttribute("userId", userId);
        return "home";
    }
}
